package com.kilometer.domain.homeModules.modules.monthlyFreeTicket;

import com.kilometer.domain.archive.QArchive;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketEntityDto;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketResponse;
import com.kilometer.domain.item.QItemEntity;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.pick.QPick;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MonthlyFreeItemRepositoryImpl implements MonthlyFreeItemRepository {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<MonthlyFreeTicketEntityDto> findRand5ByUserIdAndRequestTime(Long userId,
        LocalDateTime requestTime) {
        String query = "select\n"
            + "    i.id,\n"
            + "    i.listImageUrl,\n"
            + "    i.title,\n"
            + "    i.exhibitionType,\n"
            + "    i.feeType,\n"
            + "    i.pickCount,\n"
            + "    i.startDate,\n"
            + "    i.endDate,\n"
            + "    p.isHearted,\n"
            + "    count(a.id) as archiveCount,\n"
            + "    AVG(a.starRating) as grade\n"
            + "from item i\n"
            + "LEFT JOIN pick p on i.id = p.pickedItem and p.pickedUser = ?\n"
            + "LEFT JOIN archive a on i.id = a.item and a.isVisibleAtItem=true\n"
            + "WHERE 1 = 1\n"
            + "  AND i.feeType = 'FREE'\n"
            + "  AND i.exposureType = 'ON'\n"
            + "  AND DATE_FORMAT(i.startDate, '%Y-%m') = DATE_FORMAT(?, '%Y-%m')\n"
            + "GROUP BY i.id\n"
            + "ORDER BY RAND()\n"
            + "LIMIT 5";
        return jdbcTemplate.query(query,
            this::rowToMonthlyFreeTicketEntityDto, userId, requestTime);
    }

    private MonthlyFreeTicketEntityDto rowToMonthlyFreeTicketEntityDto(ResultSet rs, int rowNum)
        throws SQLException {
        return MonthlyFreeTicketEntityDto.builder()
            .id(rs.getLong("id"))
            .listImageUrl(rs.getString("listImageUrl"))
            .title(rs.getString("title"))
            .exhibitionType(ExhibitionType.valueOf(rs.getString("exhibitionType")))
            .feeType(FeeType.valueOf(rs.getString("feeType")))
            .startDate(rs.getTimestamp("startDate").toLocalDateTime().toLocalDate())
            .endDate(rs.getTimestamp("endDate").toLocalDateTime().toLocalDate())
            .pickCount(rs.getInt("pickCount"))
            .isHearted(rs.getBoolean("isHearted"))
            .archiveCount(rs.getLong("archiveCount"))
            .avgStarRating(rs.getDouble("grade"))
            .build();
    }
}
