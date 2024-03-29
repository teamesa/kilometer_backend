package com.kilometer.domain.pick;

import com.google.common.base.Preconditions;
import com.kilometer.domain.converter.listItem.ListItemAggregateConverter;
import com.kilometer.domain.converter.listItem.dto.ListItem;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.paging.PagingStatusService;
import com.kilometer.domain.pick.dto.MostPickItemDto;
import com.kilometer.domain.pick.dto.MostPickResponse;
import com.kilometer.domain.pick.dto.MyPickResponse;
import com.kilometer.domain.pick.dto.PickItemDto;
import com.kilometer.domain.pick.dto.PickRequest;
import com.kilometer.domain.pick.dto.PickResponse;
import com.kilometer.domain.user.User;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PickService {

    private final PickRepository pickRepository;
    private final ItemService itemService;
    private final ListItemAggregateConverter listItemAggregateConverter;
    private final PagingStatusService pagingStatusService;

    @Transactional
    public PickResponse makePickStatus(Long itemId, Long userId, boolean nextPickedStatus) {
        ItemEntity pickedItem = ItemEntity.builder().id(itemId).build();
        User pickedUser = User.builder().id(userId).build();

        Pick pick = pickRepository.getPickByPickedUserAndPickedItem(pickedUser, pickedItem)
                .orElse(
                        Pick.builder()
                                .isHearted(false)
                                .pickedItem(pickedItem)
                                .pickedUser(pickedUser)
                                .build());

        if (isAlreadyPicked(pick, nextPickedStatus)) {
            return PickResponse.builder()
                    .content(pick.isHearted())
                    .build();
        }

        pick.changeIsHearted(nextPickedStatus);
        if (nextPickedStatus) {
            itemService.plusItemPickCount(itemId);
        } else {
            itemService.minusItemPickCount(itemId);
        }

        return PickResponse.builder()
                .content(pickRepository.save(pick).isHearted())
                .build();
    }

    public MyPickResponse getMyPicks(PickRequest pickRequest, long userId) {
        Preconditions.checkNotNull(pickRequest, "pick object must not be null");
        Preconditions.checkNotNull(pickRequest.getRequestPagingStatus(), "page value must not be null");

        User pickedUser = User.builder().id(userId).build();
        Pageable pageable = pagingStatusService.makePageable(pickRequest.getRequestPagingStatus());
        Page<PickItemDto> pageablePicks = pickRepository.findAllMyPickByPickedUser(pageable, pickedUser);
        pageablePicks.stream().forEach(PickItemDto::updateApiTypeToPick);

        return convertToMyPick(pageablePicks, pageablePicks.getTotalElements());
    }

    private MyPickResponse convertToMyPick(Page<PickItemDto> pageablePicks, long pickCount) {
        List<ListItem> items = pageablePicks.stream()
                .map(listItemAggregateConverter::convert)
                .collect(Collectors.toList());

        return MyPickResponse.builder()
                .count(pickCount)
                .contents(items)
                .responsePagingStatus(pagingStatusService.convert(pageablePicks))
                .build();
    }

    public MostPickResponse getMostPicks() {
        List<MostPickItemDto> mostPickTop4 = pickRepository.findMostPickTop4(
                LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),
                                LocalDateTime.now().getDayOfMonth(), 0, 0, 0)
                        .withDayOfMonth(1));

        return convertToMostPick(mostPickTop4);
    }

    private MostPickResponse convertToMostPick(List<MostPickItemDto> items) {
        List<ListItem> itemList = items.stream()
                .map(listItemAggregateConverter::convert)
                .collect(Collectors.toList());

        return MostPickResponse.builder()
                .contents(itemList)
                .build();
    }

    private boolean isAlreadyPicked(Pick pick, boolean nextPickedStatus) {
        return pick.isHearted() == nextPickedStatus;
    }
}
