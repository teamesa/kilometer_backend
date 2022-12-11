package com.kilometer.domain.pick;

import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.pick.dto.MostPickItem;
import com.kilometer.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PickRepository extends JpaRepository<Pick, Long> {
    Optional<Pick> getPickByPickedUserAndPickedItem(User user, ItemEntity itemEntity);

    Page<Pick> findByPickedUserAndIsHeartedOrderByUpdatedAtDesc(User user, boolean isHearted, Pageable pageable);

    @Query(value = "SELECT p.pickedItem, COUNT(p.pickedItem) AS count " +
            "FROM pick p " +
            "WHERE p.isHearted = true AND p.updatedAt >= :updatedAt " +
            "GROUP BY p.pickedItem " +
            "ORDER BY count DESC " +
            "LIMIT 4", nativeQuery = true)
    List<MostPickItem> findByMostPickTop4(@Param("updatedAt") LocalDateTime firstDate);
}
