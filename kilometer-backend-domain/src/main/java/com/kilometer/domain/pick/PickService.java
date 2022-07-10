package com.kilometer.domain.pick;

import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PickService {
    private final PickRepository pickRepository;
    private final ItemService itemService;

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
}
