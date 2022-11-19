package com.kilometer.domain.homeModules.modules.monthlyFreeTicket;

import com.kilometer.domain.archive.QArchive;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketEntityDto;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketResponse;
import com.kilometer.domain.item.QItemEntity;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.pick.QPick;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class MonthlyFreeItemRepositoryImpl implements MonthlyFreeItemRepository {

    @Override
    public List<MonthlyFreeTicketEntityDto> findRand5ByUserIdAndRequestTime(Long userId,
        LocalDateTime requestTime) {
        // TODO JDBC TEMPLATE
        return List.of();
    }
}
