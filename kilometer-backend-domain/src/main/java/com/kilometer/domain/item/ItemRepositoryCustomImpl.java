package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.pick.QPick;
import com.kilometer.domain.search.request.FilterOptions;
import com.kilometer.domain.search.request.ProgressDateType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final static QItemEntity itemEntity = QItemEntity.itemEntity;
    private final static QPick pick = QPick.pick;

    public ItemRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<SearchItemResponse> findAllBySortOption(Pageable pageable, FilterOptions filterOptions, long userId) {
        List<SearchItemResponse> items = queryFactory
                .select(Projections.fields(SearchItemResponse.class,
                                itemEntity.id,
                                itemEntity.image,
                                itemEntity.title,
                                itemEntity.exhibitionType,
                                itemEntity.fee,
                                itemEntity.startDate,
                                itemEntity.endDate,
                                pick.isHearted
                        )
                )
                .from(itemEntity)
                .leftJoin(pick)
                .on(pick.pickedItem.eq(itemEntity), eqUserId(userId))
                .where(
                        itemEntity.exposureType.eq(ExposureType.ON),
                        eqExhibitionType(filterOptions),
                        eqFeeType(filterOptions),
                        eqRegionType(filterOptions),
                        eqProgressType(filterOptions)
                )
                .fetch();

        return new PageImpl<>(items, pageable, items.size());
    }

    private BooleanExpression eqExhibitionType(FilterOptions filterOptions) {
        return Optional.ofNullable(filterOptions)
                .map(FilterOptions::getExhibitionType)
                .map(itemEntity.exhibitionType::eq)
                .orElse(null);
    }

    private BooleanExpression eqFeeType(FilterOptions filterOptions) {
        return Optional.ofNullable(filterOptions)
                .map(FilterOptions::getFeeTypes)
                .map(Collection::stream)
                .map(it -> it.map(itemEntity.fee::eq))
                .flatMap(it -> it.reduce(BooleanExpression::or))
                .orElse(null);
    }

    private BooleanExpression eqRegionType(FilterOptions filterOptions) {
        return Optional.ofNullable(filterOptions)
                .map(FilterOptions::getRegionTypes)
                .map(Collection::stream)
                .map(it -> it.map(itemEntity.regionType::eq))
                .flatMap(it -> it.reduce(BooleanExpression::or))
                .orElse(null);
    }

    private BooleanExpression eqProgressType(FilterOptions filterOptions) {
        return Optional.ofNullable(filterOptions)
                .map(FilterOptions::getProgressTypes)
                .map(Collection::stream)
                .map(it -> it.map(this::getExpressionByProgressDateType))
                .flatMap(it -> it.reduce(BooleanExpression::or))
                .orElse(null);
    }

    private BooleanExpression getExpressionByProgressDateType(ProgressDateType type) {
        LocalDate now = LocalDate.now();

        switch (type) {
            case UPCOMING:
                return itemEntity.startDate.goe(now);
            case OFF:
                return itemEntity.endDate.loe(now);
            case ON:
                return itemEntity.startDate.loe(now).and(itemEntity.endDate.goe(now));
            default:
                return null;
        }
    }

    private BooleanExpression eqUserId(long userId) {
        if (-1L == userId) {
            return pick.pickedUser.id.eq(0L);
        } else {
            return pick.pickedUser.id.eq(userId);
        }
    }
}
