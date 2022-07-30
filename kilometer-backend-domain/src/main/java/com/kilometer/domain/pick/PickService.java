package com.kilometer.domain.pick;

import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.paging.PagingStatusService;
import com.kilometer.domain.pick.dto.MyPickResponse;
import com.kilometer.domain.pick.dto.PickItemResponse;
import com.kilometer.domain.pick.dto.PickResponse;
import com.kilometer.domain.search.ListItemAggregateConverter;
import com.kilometer.domain.search.dto.ListItem;
import com.kilometer.domain.search.request.SearchRequest;
import com.kilometer.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PickService {
    private final PickRepository pickRepository;
    private final ItemService itemService;
    private final ListItemAggregateConverter listItemAggregateConverter;
    private final PagingStatusService pagingStatusService;

    @Transactional
    public PickResponse makePickStatus(Long itemId, Long userId, boolean status) {
        ItemEntity pickedItem = ItemEntity.builder().id(itemId).build();
        User pickedUser = User.builder().id(userId).build();

        Pick pick = pickRepository.getPickByPickedUserAndPickedItem(pickedUser, pickedItem)
                .map(it -> it.changeIsHearted(status))
                .orElse(
                        Pick.builder()
                                .isHearted(status)
                                .pickedItem(pickedItem)
                                .pickedUser(pickedUser)
                                .build()
                );

        if (status) {
            itemService.plusItemPickCount(itemId);
        } else {
            itemService.minusItemPickCount(itemId);
        }

        return PickResponse.builder()
                .content(pickRepository.save(pick).isHearted())
                .build();
    }

    public MyPickResponse getMyPick(SearchRequest searchRequest, long userId) {
        Preconditions.notNull(searchRequest, String.format("this service can not be run will null object, please check this, %s", searchRequest));
        Preconditions.notNull(searchRequest.getRequestPagingStatus(), String.format("this service can not be run will null object, please check this, %s", searchRequest));

        User pickedUser = User.builder().id(userId).build();
        Pageable pageable = pagingStatusService.makePageable(searchRequest);

        Page<Pick> pageablePicks = pickRepository.findByPickedUser(pickedUser, pageable);
        long pickCount = pageablePicks.getTotalElements();

        return convertingItems(pageablePicks, pickCount, searchRequest.getQueryString());
    }

    private MyPickResponse convertingItems(Page<Pick> pageablePicks, long pickCount, String query) {
        List<ListItem> items = pageablePicks.stream()
                .map(PickItemResponse::makePickItemResponse)
                .collect(Collectors.toList()).stream()
                .map(listItemAggregateConverter::convert)
                .collect(Collectors.toList());

        return MyPickResponse.builder()
                .count(pickCount)
                .contents(items)
                .responsePagingStatus(pagingStatusService.convert(pageablePicks, query))
                .build();
    }
}
