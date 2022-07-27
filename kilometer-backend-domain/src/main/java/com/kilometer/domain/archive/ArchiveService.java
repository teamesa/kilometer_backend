package com.kilometer.domain.archive;

import com.kilometer.domain.archive.archiveImage.ArchiveImage;
import com.kilometer.domain.archive.archiveImage.ArchiveImageService;
import com.kilometer.domain.archive.dto.ArchiveDetailDto;
import com.kilometer.domain.archive.dto.ArchiveDetailResponse;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.ArchiveQueryRequest;
import com.kilometer.domain.archive.dto.ArchiveResponse;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveInfo;
import com.kilometer.domain.archive.dto.MyArchiveResponse;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceService;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.paging.PagingStatusService;
import com.kilometer.domain.paging.RequestPagingStatus;
import com.kilometer.domain.paging.ResponsePagingStatus;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserService;
import com.kilometer.domain.user.dto.UserResponse;
import com.kilometer.domain.util.FrontUrlUtils;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchiveService {

    private final UserService userService;
    private final ArchiveRepository archiveRepository;
    private final ArchiveImageService archiveImageService;
    private final UserVisitPlaceService userVisitPlaceService;
    private final PagingStatusService pagingStatusService;
    private final ArchiveAggregateConverter archiveAggregateConverter;

    @Transactional
    public ArchiveInfo save(Long userId, ArchiveRequest archiveRequest) {
        validateArchiveRequest(archiveRequest, userId);

        UserResponse userResponse = userService.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 사용자 정보 입니다."));

        Archive archive = saveArchive(archiveRequest, userId, archiveRequest.getItemId());
        archiveImageService.saveAll(archiveRequest, archive);
        userVisitPlaceService.saveAll(archiveRequest, archive);

        return archiveAggregateConverter.convertArchiveInfo(archive, userResponse);
    }

    @Transactional
    public ArchiveInfo update(Long userId, ArchiveRequest request) {
        Preconditions.notNull(userId, "id must not be null");
        Preconditions.notNull(request.getItemId(), "Item id must not be null");

        Archive archive = findByItemIdAndUserId(userId, request.getItemId());

        archive.update(request);
        updateArchiveImages(request, archive);
        updateUserVisitPlace(request, archive);

        return archiveAggregateConverter.convertArchiveInfo(archive);
    }

    public ArchiveResponse findAllByItemIdAndUserId(Long itemId, Long userId,
        RequestPagingStatus requestPagingStatus, ArchiveSortType sortType) {
        Preconditions.notNull(itemId, "id must not be null");
        Preconditions.notNull(userId, "id must not be null");
        Preconditions.notNull(requestPagingStatus, "page value must not be null");
        Preconditions.notNull(sortType, "sort type value must not be null");

        Pageable pageable = pagingStatusService.makePageable(requestPagingStatus, sortType);
        Page<ItemArchiveDto> items = archiveRepository.findAllByItemIdAndUserId(pageable,
            ArchiveQueryRequest.builder()
                .archiveSortType(sortType)
                .itemId(itemId)
                .userId(userId)
                .build());

        List<ArchiveInfo> archiveInfos = convertArchiveInfos(items);
        Double starRatingAvg = getStarRatingAvgByItemId(itemId);
        ResponsePagingStatus responsePagingStatus = pagingStatusService.convert(items, null);

        return convertingItemArchive(responsePagingStatus, archiveInfos, starRatingAvg);
    }

    public MyArchiveResponse findAllByUserId(Long userId, RequestPagingStatus requestPagingStatus,
        ArchiveSortType sortType) {
        Preconditions.notNull(userId, "id must not be null");
        Preconditions.notNull(requestPagingStatus, "page value must not be null");

        Pageable pageable = pagingStatusService.makePageable(requestPagingStatus, sortType);
        Page<MyArchiveDto> archives = archiveRepository.findAllByUserId(pageable,
            ArchiveQueryRequest.builder()
                .archiveSortType(sortType)
                .userId(userId)
                .build());

        List<MyArchiveInfo> myArchiveInfos = convertMyArchiveInfos(archives);
        ResponsePagingStatus responsePagingStatus = pagingStatusService.convert(archives, null);

        String title = convertMyArchiveTitle(responsePagingStatus.getTotalContentsCount());

        return convertingMyArchiveResponse(responsePagingStatus, myArchiveInfos, title);
    }

    public void updateArchiveLikeCount(boolean status, Long archiveId) {
        if (status) {
            updateArchiveLikeCount(Archive::plusLikeCount, archiveId);
        } else {
            updateArchiveLikeCount(Archive::minusLikeCount, archiveId);
        }
    }

    public ArchiveDetailResponse findByArchiveIdAndUserId(Long archiveId, Long userId) {
        Preconditions.notNull(archiveId, "Archive id must not be null : " + archiveId);
        Preconditions.notNull(userId, "User id must not be null : " + userId);

        ArchiveDetailDto archiveDetailDto = archiveRepository.findAllByArchiveIdAndUserId(
                archiveId, userId).stream().findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Archive does not exist"));

        List<UserVisitPlace> userVisitPlaces = userVisitPlaceService.findAllByArchiveId(
            archiveDetailDto.getId());
        List<ArchiveImage> archiveImages = archiveImageService.findAllByArchiveId(
            archiveDetailDto.getId());

        return archiveAggregateConverter.convertArchiveDetail(archiveDetailDto, userVisitPlaces, archiveImages);
    }


    private void validateArchiveRequest(ArchiveRequest archiveRequest, Long userId) {
        Preconditions.notNull(archiveRequest.getComment(),
            "Comment must not be null");
        Preconditions.condition(
            (archiveRequest.getStarRating() > 0 && archiveRequest.getStarRating() <= 5),
            "별점은 1이상 5이하의 숫자여야 합니다. now : " + archiveRequest.getStarRating());
        Preconditions.notNull(archiveRequest.getPhotoUrls(),
            "Photo urls must not be null");
        Preconditions.notNull(archiveRequest.getPlaceInfos(),
            "Place infos must not be null");

        Preconditions.condition(
            !archiveRepository.existsByItemIdAndUserId(archiveRequest.getItemId(), userId),
            String.format("기 등록한 Archive가 있습니다. sItemId : %d / UserId : %d",
                archiveRequest.getItemId(), userId));
    }

    private Archive saveArchive(ArchiveRequest archiveRequest, Long userId, Long itemId) {
        Archive archive = archiveRequest.makeArchive();
        archive.setUser(User.builder().id(userId).build());
        archive.setItem(ItemEntity.builder().id(itemId).build());
        archiveRepository.save(archive);
        return archive;
    }

    private void updateArchiveImages(ArchiveRequest archiveRequest, Archive archive) {
        archiveImageService.deleteAll(archive.getArchiveImages());
        archiveImageService.saveAll(archiveRequest, archive);
    }

    private void updateUserVisitPlace(ArchiveRequest archiveRequest, Archive archive) {
        userVisitPlaceService.deleteAll(archive.getUserVisitPlaces());
        userVisitPlaceService.saveAll(archiveRequest, archive);
    }

    private Double getStarRatingAvgByItemId(Long itemId) {
        Double result = archiveRepository.avgStarRatingByItemId(itemId);
        if (result != null) {
            result = Math.round(result * 10) / 10.0;
        }
        return result;
    }

    private Archive findByItemIdAndUserId(Long userId, Long itemId) {
        return archiveRepository.findByItemIdAndUserId(itemId, userId)
            .orElseThrow(() -> new IllegalArgumentException(
                "존재하지 않는 요청입니다. itemId : " + itemId + "/ userId : " + userId));
    }

    private List<ArchiveInfo> convertArchiveInfos(Page<ItemArchiveDto> items) {
        return items.stream()
            .map(itemArchiveDto -> {
                List<ArchiveImage> archiveImages = archiveImageService.findAllByArchiveId(
                    itemArchiveDto.getId());
                List<UserVisitPlace> userVisitPlaces = userVisitPlaceService.findAllByArchiveId(
                    itemArchiveDto.getId());
                return archiveAggregateConverter.convertArchiveInfo(itemArchiveDto, archiveImages,
                    userVisitPlaces);
            })
            .collect(Collectors.toList());
    }

    private List<MyArchiveInfo> convertMyArchiveInfos(Page<MyArchiveDto> archives) {
        return archives.stream()
            .map(myArchiveDto -> {
                boolean existImages = archiveImageService.existArchiveImagesByArchiveId(
                    myArchiveDto.getId());
                List<UserVisitPlace> userVisitPlaces = userVisitPlaceService.findAllByArchiveId(
                    myArchiveDto.getId());
                return archiveAggregateConverter.convertMyArchiveInfo(myArchiveDto, existImages,
                    userVisitPlaces);
            })
            .collect(Collectors.toList());
    }

    private ArchiveResponse convertingItemArchive(ResponsePagingStatus responsePagingStatus,
        List<ArchiveInfo> archiveInfos, Double avgStarRating) {
        return ArchiveResponse.builder()
            .responsePagingStatus(responsePagingStatus)
            .avgStarRating(avgStarRating)
            .archives(archiveInfos)
            .build();
    }

    private MyArchiveResponse convertingMyArchiveResponse(ResponsePagingStatus responsePagingStatus,
        List<MyArchiveInfo> myArchiveInfos, String convertedTitle) {
        return MyArchiveResponse.builder()
            .title(convertedTitle)
            .contents(myArchiveInfos)
            .responsePagingStatus(responsePagingStatus)
            .build();
    }

    private String convertMyArchiveTitle(long totalContentsCount) {
        if (totalContentsCount == 0) {
            return FrontUrlUtils.getFrontMyArchiveTitle();
        }
        return FrontUrlUtils.getFrontMyArchiveTitlePattern(totalContentsCount);
    }

    private void updateArchiveLikeCount(Function<Archive, Archive> mapper, Long archiveId) {
        Function<Long, Archive> generated = it -> archiveRepository.findById(archiveId)
            .map(mapper)
            .map(archiveRepository::save)
            .orElseThrow(() -> new IllegalArgumentException("Archive가 존재하지 않습니다. id = " + it));
        generated.apply(archiveId);
    }
}
