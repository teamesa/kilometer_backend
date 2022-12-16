package com.kilometer.domain.pick;

import com.kilometer.domain.item.QItemEntity;
import com.kilometer.domain.pick.dto.MostPickItemDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("rawtypes")
public class PickRepositoryCustomImpl implements PickRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final static QItemEntity itemEntity = QItemEntity.itemEntity;
    private final static QPick pick = QPick.pick;

    public PickRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<MostPickItemDto> findMostPickTop4(LocalDateTime firstDate) {
        NumberPath<Long> pickCount = Expressions.numberPath(Long.class, "pickCount");

        return queryFactory
                .select(Projections.fields(MostPickItemDto.class,
                                itemEntity.id,
                                itemEntity.listImageUrl,
                                itemEntity.thumbnailImageUrl,
                                itemEntity.title,
                                itemEntity.exhibitionType,
                                itemEntity.feeType,
                                itemEntity.startDate,
                                itemEntity.endDate,
                                pick.isHearted,
                                pick.pickedItem.count().as(pickCount)
                        )
                )
                .from(itemEntity)
                .leftJoin(pick)
                .on(pick.pickedItem.eq(itemEntity))
                .where(pick.isHearted.eq(true).and(pick.updatedAt.goe(firstDate)))
                .groupBy(pick.pickedItem)
                .orderBy(pickCount.desc())
                .limit(4)
                .fetch();
    }
}
