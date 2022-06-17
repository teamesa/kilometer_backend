package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.ArchiveResponse;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.entity.Archive;
import com.kilometer.domain.archive.entity.ArchiveImage;
import com.kilometer.domain.archive.entity.UserVisitPlace;
import com.kilometer.domain.archive.queryDto.ArchiveSelectResult;
import com.kilometer.domain.archive.repository.ArchiveImageRepository;
import com.kilometer.domain.archive.repository.ArchiveRepository;
import com.kilometer.domain.archive.repository.UserVisitPlaceRepository;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.paging.PagingStatusService;
import com.kilometer.domain.paging.RequestPagingStatus;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
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

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ArchiveRepository archiveRepository;
    private final ArchiveImageRepository archiveImageRepository;
    private final UserVisitPlaceRepository userVisitPlaceRepository;
    private final PagingStatusService pagingStatusService;

    @Transactional
    public ArchiveInfo save(Long userId, ArchiveRequest archiveRequest) {
        Preconditions.notNull(archiveRequest.getComment(),
            "Comment must not be null");
        Preconditions.condition(
            (archiveRequest.getStarRating() > 0 && archiveRequest.getStarRating() <= 5),
            "별점은 1이상 5이하의 숫자여야 합니다. now : " + archiveRequest.getStarRating());
        Preconditions.notNull(archiveRequest.getPhotoUrls(),
            "Photo urls must not be null");
        Preconditions.notNull(archiveRequest.getPlaceInfos(),
            "Place infos must not be null");

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + userId));

        ItemEntity item = itemRepository.findById(archiveRequest.getItemId())
            .orElseThrow(() -> new IllegalArgumentException(
                "Item does not exists 없습니다. id = " + archiveRequest.getItemId()));

        List<Archive> findedArchive = archiveRepository.findAllByItemAndUser(item, user);

        if (!findedArchive.isEmpty()) {
            throw new IllegalArgumentException("이미 Archive가 존재합니다. id : " + findedArchive.get(0));
        }

        Archive archive = saveArchive(archiveRequest, user, item);
        saveArchiveImages(archiveRequest, archive);
        saveUserVisitPlace(archiveRequest, archive);

        return archive.makeInfo();
    }

    private Archive saveArchive(ArchiveRequest archiveRequest, User user, ItemEntity item) {
        Archive archive = archiveRequest.makeArchive();
        archive.setUser(user);
        archive.setItem(item);
        archiveRepository.save(archive);
        return archive;
    }

    private void saveArchiveImages(ArchiveRequest archiveRequest, Archive archive) {
        if (archiveRequest.getPhotoUrls().isEmpty()) {
            return;
        }
        List<ArchiveImage> archiveImages = archiveRequest.makeArchiveImages();
        archiveImages.forEach(archiveImage -> archiveImage.setArchive(archive));
        archiveImageRepository.saveAll(archiveImages);
    }

    private void saveUserVisitPlace(ArchiveRequest archiveRequest, Archive archive) {
        if (archiveRequest.getPlaceInfos().isEmpty()) {
            return;
        }
        List<UserVisitPlace> userVisitPlaces = archiveRequest.makeVisitedPlace();
        userVisitPlaces.forEach(userVisitPlace -> userVisitPlace.setArchive(archive));
        userVisitPlaceRepository.saveAll(userVisitPlaces);
    }

    public ArchiveResponse findAllByItemId(Long itemId, RequestPagingStatus requestPagingStatus,
        ArchiveSortType sortType) {
        Preconditions.notNull(itemId, "id must not be null");
        Preconditions.notNull(requestPagingStatus, "page value must not be null");
        Preconditions.notNull(sortType, "sort type value must not be null");

        Pageable pageable = pagingStatusService.makePageable(requestPagingStatus, sortType);
        Page<ArchiveSelectResult> items = archiveRepository.findAllByItemId(pageable, sortType,
            itemId);

        return convertingItemArchive(items, getStarRatingAvgByItemId(itemId));
    }

    public ArchiveResponse findAllByUserId(Long userId, RequestPagingStatus requestPagingStatus) {
        Preconditions.notNull(userId, "id must not be null");
        Preconditions.notNull(requestPagingStatus, "page value must not be null");

        return ArchiveResponse.builder().build();
    }

    private ArchiveResponse convertingItemArchive(Page<ArchiveSelectResult> archiveSelectResults,
        Double avgStarRating) {
        List<ArchiveInfo> infos = archiveSelectResults.stream().map(ArchiveSelectResult::convert)
            .collect(Collectors.toList());
        return ArchiveResponse.builder()
            .responsePagingStatus(pagingStatusService.convert(archiveSelectResults, null))
            .avgStarRating(avgStarRating)
            .archives(infos)
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
        deleteArchiveImages(archive);
        saveArchiveImages(archiveRequest, archive);
    }

    private void deleteArchiveImages(Archive archive) {
        if (archive.getArchiveImages().isEmpty()) {
            return;
        }
        archiveImageRepository.deleteAll(archive.getArchiveImages());
    }

    private void updateUserVisitPlace(ArchiveRequest archiveRequest, Archive archive) {
        deleteUserVisitPlace(archive);
        saveUserVisitPlace(archiveRequest, archive);
    }

    private void deleteUserVisitPlace(Archive archive) {
        if (archive.getUserVisitPlaces().isEmpty()) {
            return;
        }
        userVisitPlaceRepository.deleteAll(archive.getUserVisitPlaces());
    }

    private Archive findByItemIdAndUserId(Long userId, Long itemId) {
        return archiveRepository.findByItemIdAndUserId(itemId, userId)
            .orElseThrow(() -> new IllegalArgumentException(
                "존재하지 않는 요청입니다. itemId : " + itemId + "/ userId : " + userId));
    }
}
