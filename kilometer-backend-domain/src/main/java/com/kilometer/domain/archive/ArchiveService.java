package com.kilometer.domain.archive;

import com.google.common.base.Preconditions;
import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.archiveImage.ArchiveImageService;
import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.dto.ArchiveDeleteResponse;
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
import com.kilometer.domain.archive.exception.ArchiveNotFoundException;
import com.kilometer.domain.archive.generator.ArchiveRatingCalculator;
import com.kilometer.domain.archive.like.LikeService;
import com.kilometer.domain.archive.like.dto.LikeDto;
import com.kilometer.domain.archive.like.dto.LikeResponse;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
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
    private final LikeService likeService;

    @Transactional
    public ArchiveInfo save(Long userId, ArchiveRequest archiveRequest) {
        validateArchiveRequest(archiveRequest, userId);

        Archive archive = archiveRequest.toDomain();
        archive.validate();

        UserResponse userResponse = userService.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 사용자 정보 입니다."));

        ArchiveEntity archiveEntity = saveArchive(archiveRequest, userId, archiveRequest.getItemId());

        Long archiveId = archiveEntity.getId();
        List<ArchiveImageEntity> archiveImageEntities = archiveRequest.makeArchiveImages();
        List<UserVisitPlaceEntity> userVisitPlaceEntities = archiveRequest.makeVisitedPlace();
        archiveImageService.saveAll(archiveImageEntities, archiveId);
        userVisitPlaceService.saveAll(userVisitPlaceEntities, archiveId);

        return archiveAggregateConverter.convertArchiveInfo(archiveEntity, userResponse, archiveImageEntities,
            userVisitPlaceEntities);
    }

    @Transactional
    public ArchiveInfo update(Long userId, ArchiveRequest request) {
        Preconditions.checkNotNull(userId, "id must not be null");
        Preconditions.checkNotNull(request.getItemId(), "Item id must not be null");

        ArchiveEntity archiveEntity = archiveRepository.findByItemIdAndUserId(request.getItemId(), userId)
            .orElseThrow(ArchiveNotFoundException::new);

        Long archiveId = archiveEntity.getId();
        List<ArchiveImageEntity> archiveImageEntities = request.makeArchiveImages();
        List<UserVisitPlaceEntity> userVisitPlaceEntities = request.makeVisitedPlace();

        updateArchiveImages(archiveImageEntities, archiveId);
        updateUserVisitPlace(userVisitPlaceEntities, archiveId);

        archiveEntity.update(request);

        return archiveAggregateConverter.convertArchiveInfo(archiveEntity, archiveImageEntities,
            userVisitPlaceEntities);
    }

    public ArchiveResponse findAllByItemIdAndUserId(Long itemId, Long userId,
                                                    RequestPagingStatus requestPagingStatus, ArchiveSortType sortType) {
        Preconditions.checkNotNull(itemId, "id must not be null");
        Preconditions.checkNotNull(userId, "id must not be null");
        Preconditions.checkNotNull(requestPagingStatus, "page value must not be null");
        Preconditions.checkNotNull(sortType, "sort type value must not be null");

        Pageable pageable = pagingStatusService.makePageable(requestPagingStatus, sortType);
        Page<ItemArchiveDto> items = archiveRepository.findAllItemArchiveByArchiveQueryRequest(
            pageable,
            ArchiveQueryRequest.builder()
                .archiveSortType(sortType)
                .itemId(itemId)
                .userId(userId)
                .isVisible(true)
                .build());

        List<ArchiveInfo> archiveInfos = convertArchiveInfos(items);
        Double starRatingAvg = getStarRatingAvgByItemId(itemId);
        ResponsePagingStatus responsePagingStatus = pagingStatusService.convert(items, null);

        return convertingItemArchive(responsePagingStatus, archiveInfos, starRatingAvg);
    }

    public MyArchiveResponse findAllByUserId(Long userId, RequestPagingStatus requestPagingStatus,
                                             ArchiveSortType sortType) {
        Preconditions.checkNotNull(userId, "id must not be null");
        Preconditions.checkNotNull(requestPagingStatus, "page value must not be null");

        Pageable pageable = pagingStatusService.makePageable(requestPagingStatus, sortType);
        Page<MyArchiveDto> archives = archiveRepository.findAllMyArchiveByArchiveQueryRequest(
            pageable,
            ArchiveQueryRequest.builder()
                .archiveSortType(sortType)
                .userId(userId)
                .isVisible(false)
                .build());

        List<MyArchiveInfo> myArchiveInfos = convertMyArchiveInfos(archives);
        ResponsePagingStatus responsePagingStatus = pagingStatusService.convert(archives, null);

        String title = convertMyArchiveTitle(responsePagingStatus.getTotalContentsCount());

        return convertingMyArchiveResponse(responsePagingStatus, myArchiveInfos, title);
    }

    @Transactional
    public ArchiveDeleteResponse delete(Long archiveId, Long userId) throws IllegalAccessException {
        Preconditions.checkNotNull(archiveId, "id must not be null");

        ArchiveEntity archiveEntity = archiveRepository.findById(archiveId)
            .orElseThrow(() -> new IllegalArgumentException("Archive does not exists."));

        if (!userId.equals(archiveEntity.getUser().getId())) {
            throw new IllegalAccessException("Archives can only be deleted by the writer.");
        }

        likeService.deleteAll(archiveId);
        archiveImageService.deleteAllByArchiveId(archiveId);
        userVisitPlaceService.deleteAllByArchiveId(archiveId);
        archiveRepository.delete(archiveEntity);

        return ArchiveDeleteResponse.from(archiveId);
    }

    @Transactional
    public LikeResponse like(Long archiveId, Long userId, boolean status) {
        LikeDto archiveLike = likeService.findByArchiveIdAndUserId(archiveId, userId);

        if (status != archiveLike.isLiked()) {
            updateArchiveLikeCount(status, archiveId);
            likeService.updateLikeStatus(status, archiveLike.getId(), archiveId, userId);
        }
        return LikeResponse.builder().content(status).build();
    }

    public ArchiveDetailResponse findByArchiveIdAndUserId(Long archiveId, Long userId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        Preconditions.checkNotNull(userId, "User id must not be null : " + userId);

        ArchiveDetailDto archiveDetailDto = archiveRepository.findByArchiveIdAndUserIdAndIsVisible(
                archiveId,
                userId, false)
            .orElseThrow(ArchiveNotFoundException::new);

        List<UserVisitPlaceEntity> userVisitPlaceEntities = userVisitPlaceService.findAllByArchiveId(
            archiveDetailDto.getId());
        List<ArchiveImageEntity> archiveImageEntities = archiveImageService.findAllByArchiveId(
            archiveDetailDto.getId());

        return archiveAggregateConverter.convertArchiveDetail(archiveDetailDto, userVisitPlaceEntities,
            archiveImageEntities);
    }

    public Long findArchiveIdByItemIdAndUserId(Long itemId, Long userId) {
        Preconditions.checkNotNull(itemId, "Archive id must not be null : " + itemId);
        Preconditions.checkNotNull(userId, "User id must not be null : " + userId);

        return archiveRepository.findByItemIdAndUserId(itemId, userId)
            .map(ArchiveEntity::getId)
            .orElse(null);
    }


    private void validateArchiveRequest(ArchiveRequest archiveRequest, Long userId) {
        Preconditions.checkArgument(
            !archiveRepository.existsByItemIdAndUserId(archiveRequest.getItemId(), userId),
            String.format("기 등록한 Archive가 있습니다. sItemId : %d / UserId : %d",
                archiveRequest.getItemId(), userId));
    }

    private ArchiveEntity saveArchive(ArchiveRequest archiveRequest, Long userId, Long itemId) {
        ArchiveEntity archiveEntity = archiveRequest.makeArchive();
        archiveEntity.setUser(User.builder().id(userId).build());
        archiveEntity.setItem(ItemEntity.builder().id(itemId).build());
        archiveRepository.save(archiveEntity);
        return archiveEntity;
    }

    private List<ArchiveImageEntity> updateArchiveImages(List<ArchiveImageEntity> newArchiveImageEntities,
                                                         Long archiveId) {
        archiveImageService.deleteAllByArchiveId(archiveId);
        return archiveImageService.saveAll(newArchiveImageEntities, archiveId);
    }

    private List<UserVisitPlaceEntity> updateUserVisitPlace(List<UserVisitPlaceEntity> newUserVisitPlaceEntities,
                                                            Long archiveId) {
        userVisitPlaceService.deleteAllByArchiveId(archiveId);
        return userVisitPlaceService.saveAll(newUserVisitPlaceEntities, archiveId);
    }

    private void updateArchiveLikeCount(boolean status, Long archiveId) {
        if (status) {
            updateArchiveLikeCount(ArchiveEntity::plusLikeCount, archiveId);
        } else {
            updateArchiveLikeCount(ArchiveEntity::minusLikeCount, archiveId);
        }
    }

    private Double getStarRatingAvgByItemId(Long itemId) {
        Double result = archiveRepository.avgStarRatingByItemId(itemId);
        return ArchiveRatingCalculator.convertArchiveRatingAverage(result);
    }

    private List<ArchiveInfo> convertArchiveInfos(Page<ItemArchiveDto> items) {
        return items.stream()
            .map(itemArchiveDto -> {
                List<ArchiveImageEntity> archiveImageEntities = archiveImageService.findAllByArchiveId(
                    itemArchiveDto.getId());
                List<UserVisitPlaceEntity> userVisitPlaceEntities = userVisitPlaceService.findAllByArchiveId(
                    itemArchiveDto.getId());
                return archiveAggregateConverter.convertArchiveInfo(itemArchiveDto, archiveImageEntities,
                    userVisitPlaceEntities);
            })
            .collect(Collectors.toList());
    }

    private List<MyArchiveInfo> convertMyArchiveInfos(Page<MyArchiveDto> archives) {
        return archives.stream()
            .map(myArchiveDto -> {
                boolean existImages = archiveImageService.existArchiveImagesByArchiveId(
                    myArchiveDto.getId());
                List<UserVisitPlaceEntity> userVisitPlaceEntities = userVisitPlaceService.findAllByArchiveId(
                    myArchiveDto.getId());
                return archiveAggregateConverter.convertMyArchiveInfo(myArchiveDto, existImages,
                    userVisitPlaceEntities);
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

    private void updateArchiveLikeCount(Function<ArchiveEntity, ArchiveEntity> mapper, Long archiveId) {
        Function<Long, ArchiveEntity> generated = it -> archiveRepository.findById(archiveId)
            .map(mapper)
            .map(archiveRepository::save)
            .orElseThrow(() -> new IllegalArgumentException("Archive가 존재하지 않습니다. id = " + it));
        generated.apply(archiveId);
    }
}
