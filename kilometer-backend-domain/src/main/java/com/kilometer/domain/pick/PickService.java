package com.kilometer.domain.pick;

import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickService {
    private final PickRepository pickRepository;

    public boolean makePickStatus(Long itemId, Long userId, boolean status) {
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

        return pickRepository.save(pick).isHearted();
    }
}
