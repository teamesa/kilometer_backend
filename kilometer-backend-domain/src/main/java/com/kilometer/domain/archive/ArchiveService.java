package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.ArchiveResponse;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.dto.PlaceInfo;
import com.kilometer.domain.archive.queryDto.ArchiveSelectResult;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.paging.PagingStatusService;
import com.kilometer.domain.paging.RequestPagingStatus;
import com.kilometer.domain.paging.ResponsePagingStatus;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArchiveService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ArchiveRepository archiveRepository;
    private final PagingStatusService pagingStatusService;

    public void save(Long userId, ArchiveRequest archiveRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + userId));

        ItemEntity item = itemRepository.findById(archiveRequest.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + archiveRequest.getItemId()));


        List<VisitedPlace> places = new ArrayList<>();
        for(PlaceInfo info : archiveRequest.getPlaceInfos()) {
            places.add(VisitedPlace.builder()
                            .placeType(PlaceType.valueOf(info.getPlaceType()))
                            .placeName(info.getName())
                            .address(info.getAddress())
                            .roadAddress(info.getRoadAddress())
                    .build());
        }

        Archive archive = Archive.builder()
                .comment(archiveRequest.getComment())
                .starRating(archiveRequest.getStarRating())
                .isVisibleAtItem(archiveRequest.isVisibleAtItem())
                .user(user)
                .item(item)
                .build();

        archive.setVisitedPlaces(places);

        archiveRepository.save(archive);

    }

    public ArchiveResponse findAllByItemId(Long itemId, RequestPagingStatus requestPagingStatus, ArchiveSortType sortType) {
        Preconditions.notNull(itemId, "id must not be null");
        Preconditions.notNull(requestPagingStatus, "page value must not be null");
        Preconditions.notNull(sortType, "sort type value must not be null");

        Pageable pageable = pagingStatusService.makePageable(requestPagingStatus,sortType);
        Page<ArchiveSelectResult> items = archiveRepository.findAllByItemId(pageable, itemId);
        Double avgStarRating = Math.round(archiveRepository.avgStarRatingByItemId(itemId)*10)/10.0;

        return convertingItemArchive(items,avgStarRating);
    }

    public ArchiveResponse findAllByUserId(Long userId, RequestPagingStatus requestPagingStatus) {
        Preconditions.notNull(userId, "id must not be null");
        Preconditions.notNull(requestPagingStatus, "page value must not be null");


        return ArchiveResponse.builder().build();
    }

    private ArchiveResponse convertingItemArchive(Page<ArchiveSelectResult> archiveSelectResults, Double avgStarRating) {
        List<ArchiveInfo> infos = archiveSelectResults.stream().map(ArchiveSelectResult::convert).collect(Collectors.toList());
        return ArchiveResponse.builder()
                .responsePagingStatus(pagingStatusService.convert(archiveSelectResults))
                .avgStarRating(avgStarRating)
                .archives(infos)
                .build();
    }

}
