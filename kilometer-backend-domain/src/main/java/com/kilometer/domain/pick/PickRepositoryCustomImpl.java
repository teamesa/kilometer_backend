package com.kilometer.domain.pick;

import com.kilometer.domain.item.QItemEntity;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.pick.dto.MostPickItemDto;
import com.kilometer.domain.pick.dto.PickItemDto;
import com.kilometer.domain.user.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    public Page<PickItemDto> findAllMyPickByPickedUser(Pageable pageable, User pickedUser) {
        List<PickItemDto> picks = queryFactory
                .select(Projections.fields(PickItemDto.class,
                                itemEntity.id,
                                itemEntity.listImageUrl,
                                itemEntity.thumbnailImageUrl,
                                itemEntity.title,
                                itemEntity.exhibitionType,
                                itemEntity.feeType,
                                itemEntity.startDate,
                                itemEntity.endDate,
                                pick.isHearted
                        )
                ).from(pick)
                .leftJoin(itemEntity)
                .on(pick.pickedItem.eq(itemEntity))
                .where(
                        pick.isHearted.eq(true),
                        itemEntity.exposureType.eq(ExposureType.valueOf("ON")),
                        pick.pickedUser.eq(pickedUser)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(pick.updatedAt.desc())
                .fetch();

        int count = queryFactory
                .select(pick.id)
                .from(pick).leftJoin(itemEntity)
                .on(pick.pickedItem.eq(itemEntity))
                .where(
                        pick.isHearted.eq(true),
                        itemEntity.exposureType.eq(ExposureType.valueOf("ON")),
                        pick.pickedUser.eq(pickedUser)
                )
                .fetch().size();

        return new PageImpl<>(picks, pageable, count);
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
                .where(
                        pick.isHearted.eq(true),
                        pick.updatedAt.goe(firstDate),
                        itemEntity.exposureType.eq(ExposureType.valueOf("ON"))
                )
                .groupBy(pick.pickedItem)
                .orderBy(pickCount.desc())
                .limit(4)
                .fetch();
    }
}
