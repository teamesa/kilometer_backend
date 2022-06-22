package com.kilometer.domain.archive;

import com.kilometer.domain.archive.archiveImage.ArchiveImage;
import com.kilometer.domain.archive.archiveImage.ArchiveImageService;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.ArchiveResponse;
import com.kilometer.domain.archive.dto.ArchiveFetchUser;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceService;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.paging.PagingStatusService;
import com.kilometer.domain.paging.RequestPagingStatus;
import com.kilometer.domain.paging.ResponsePagingStatus;
import com.kilometer.domain.user.User;
import java.util.List;
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

    private final ArchiveRepository archiveRepository;
    private final ArchiveImageService archiveImageService;
    private final UserVisitPlaceService userVisitPlaceService;
    private final PagingStatusService pagingStatusService;

    @Transactional
    public ArchiveInfo save(Long userId, ArchiveRequest archiveRequest) {
        archiveRequestValidate(archiveRequest);

        List<Archive> findArchive = archiveRepository.findAllByItemIdAndUserId(
            archiveRequest.getItemId(), userId);

        if (!findArchive.isEmpty()) {
            throw new IllegalArgumentException("이미 Archive가 존재합니다. id : " + findArchive.get(0));
        }

        Archive archive = saveArchive(archiveRequest, userId, archiveRequest.getItemId());
        archiveImageService.saveAll(archiveRequest, archive);
        userVisitPlaceService.saveAll(archiveRequest, archive);

        return archive.makeInfo();
    }

    private void archiveRequestValidate(ArchiveRequest archiveRequest) {
        Preconditions.notNull(archiveRequest.getComment(),
            "Comment must not be null");
        Preconditions.condition(
            (archiveRequest.getStarRating() > 0 && archiveRequest.getStarRating() <= 5),
            "별점은 1이상 5이하의 숫자여야 합니다. now : " + archiveRequest.getStarRating());
        Preconditions.notNull(archiveRequest.getPhotoUrls(),
            "Photo urls must not be null");
        Preconditions.notNull(archiveRequest.getPlaceInfos(),
            "Place infos must not be null");
    }

    private Archive saveArchive(ArchiveRequest archiveRequest, Long userId, Long itemId) {
        Archive archive = archiveRequest.makeArchive();
        archive.setUser(User.builder().id(userId).build());
        archive.setItem(ItemEntity.builder().id(itemId).build());
        archiveRepository.save(archive);
        return archive;
    }

    public ArchiveResponse findAllByItemId(Long itemId, RequestPagingStatus requestPagingStatus,
        ArchiveSortType sortType) {
        Preconditions.notNull(itemId, "id must not be null");
        Preconditions.notNull(requestPagingStatus, "page value must not be null");
        Preconditions.notNull(sortType, "sort type value must not be null");

        Pageable pageable = pagingStatusService.makePageable(requestPagingStatus, sortType);
        Page<ArchiveFetchUser> items = archiveRepository.findAllByItemId(pageable, sortType,
            itemId);

        List<ArchiveInfo> archiveInfos = convertArchiveInfos(items);
        Double starRatingAvg = getStarRatingAvgByItemId(itemId);
        ResponsePagingStatus responsePagingStatus = pagingStatusService.convert(items, null);

        return convertingItemArchive(responsePagingStatus, archiveInfos, starRatingAvg);
    }

    private List<ArchiveInfo> convertArchiveInfos(Page<ArchiveFetchUser> items) {
       return items.stream()
            .map(archiveFetchUser -> {
                List<ArchiveImage> archiveImages = archiveImageService.findAllByArchiveId(archiveFetchUser.getId());
                List<UserVisitPlace> userVisitPlaces = userVisitPlaceService.findAllByArchiveId(
                    archiveFetchUser.getId());
                return ArchiveInfo.makeInfo(archiveFetchUser, archiveImages, userVisitPlaces);
            })
            .collect(Collectors.toList());
    }

    public ArchiveResponse findAllByUserId(Long userId, RequestPagingStatus requestPagingStatus) {
        Preconditions.notNull(userId, "id must not be null");
        Preconditions.notNull(requestPagingStatus, "page value must not be null");

        return ArchiveResponse.builder().build();
    }

    private ArchiveResponse convertingItemArchive(ResponsePagingStatus responsePagingStatus, List<ArchiveInfo> archiveInfos,         Double avgStarRating) {
        return ArchiveResponse.builder()
            .responsePagingStatus(responsePagingStatus)
            .avgStarRating(avgStarRating)
            .archives(archiveInfos)
            .build();
    }

    private Double getStarRatingAvgByItemId(Long itemId) {
        Double result = archiveRepository.avgStarRatingByItemId(itemId);
        if (result != null) {
            result = Math.round(result * 10) / 10.0;
        }
        return result;
    }

    @Transactional
    public ArchiveInfo update(Long userId, ArchiveRequest request) {
        Preconditions.notNull(userId, "id must not be null");
        Preconditions.notNull(request.getItemId(), "Item id must not be null");

        Archive archive = findByItemIdAndUserId(userId, request.getItemId());

        archive.update(request);
        updateArchiveImages(request, archive);
        updateUserVisitPlace(request, archive);

        return archive.makeInfo();
    }

    private void updateArchiveImages(ArchiveRequest archiveRequest, Archive archive) {
        archiveImageService.deleteAll(archive.getArchiveImages());
        archiveImageService.saveAll(archiveRequest, archive);
    }

    private void updateUserVisitPlace(ArchiveRequest archiveRequest, Archive archive) {
        userVisitPlaceService.deleteAll(archive.getUserVisitPlaces());
        userVisitPlaceService.saveAll(archiveRequest, archive);
    }


    private Archive findByItemIdAndUserId(Long userId, Long itemId) {
        return archiveRepository.findByItemIdAndUserId(itemId, userId)
            .orElseThrow(() -> new IllegalArgumentException(
                "존재하지 않는 요청입니다. itemId : " + itemId + "/ userId : " + userId));
    }
}
