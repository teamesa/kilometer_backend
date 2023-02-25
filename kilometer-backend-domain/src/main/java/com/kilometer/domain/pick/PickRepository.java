package com.kilometer.domain.pick;

import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PickRepository extends JpaRepository<Pick, Long>, PickRepositoryCustom {
    Optional<Pick> getPickByPickedUserAndPickedItem(User user, ItemEntity itemEntity);
}
