package com.kilometer.domain.homeModules.modules.monthlyFreeTicket;

import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicket;
import com.kilometer.domain.item.ItemEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface MonthlyFreeItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query(value =
        "select i.id,"
            + " i.thumbnailImageUrl,"
            + " i.title,"
            + " i.exhibitionType,"
            + " i.feeType,"
            + " i.pickCount,"
            + " i.startDate,"
            + " i.endDate,"
            + " p.isHearted,"
            + " count(a.id) as archiveCount,"
            + " AVG(a.starRating) as grade "
            + "FROM item as i"
            + " LEFT JOIN pick p on i.id = p.pickedItem and p.pickedUser = :pickedUser"
            + " LEFT JOIN archive a on i.id = a.item and a.isVisibleAtItem=true "
            + "WHERE 1 = 1 "
            + " AND i.feeType = 'FREE'"
            + " AND i.exposureType = 'ON'"
            + " AND i.startDate <= :requestTime"
            + " AND i.endDate > :requestTime "
            + "GROUP BY i.id "
            + "ORDER BY RAND() "
            + "LIMIT 5", nativeQuery = true)
    List<MonthlyFreeTicket> findRand5ByUserIdAndRequestTime(@Param("pickedUser")Long userId, @Param("requestTime")LocalDateTime requestTime);

}
