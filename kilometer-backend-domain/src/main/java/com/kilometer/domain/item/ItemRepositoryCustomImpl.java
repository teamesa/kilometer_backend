package com.kilometer.domain.item;

import com.kilometer.domain.search.request.FilterOptions;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.kilometer.domain.search.request.ProgressDateType.*;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final static QItemEntity itemEntity = QItemEntity.itemEntity;

    public ItemRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<ItemEntity> findAllBySortOption(Pageable pageable, FilterOptions filterOptions) {
        List<ItemEntity> items = queryFactory
                .select(itemEntity)
                .from(itemEntity)
                .where(
                        eqExhibitionType(filterOptions),
                        eqFeeType(filterOptions),
                        eqRegionType(filterOptions),
                        loeStartDateNow(filterOptions),
                        goeEndDateNow(filterOptions),
                        goeStartDateNowLoeEndDateNow(filterOptions)
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
                .map(FilterOptions::getFeeType)
                .map(itemEntity.fee::eq)
                .orElse(null);
    }

    private BooleanExpression eqRegionType(FilterOptions filterOptions) {
        return Optional.ofNullable(filterOptions)
                .map(FilterOptions::getRegionType)
                .map(itemEntity.regionType::eq)
                .orElse(null);
    }

    private BooleanExpression loeStartDateNow(FilterOptions filterOptions) {
        return Optional.ofNullable(filterOptions)
                .map(FilterOptions::getProgressType)
                .filter(UPCOMING::equals)
                .map(it -> itemEntity.startDate.loe(LocalDate.now()))
                .orElse(null);
    }

    private BooleanExpression goeEndDateNow(FilterOptions filterOptions) {
        return Optional.ofNullable(filterOptions)
                .map(FilterOptions::getProgressType)
                .filter(OFF::equals)
                .map(it -> itemEntity.endDate.goe(LocalDate.now()))
                .orElse(null);
    }

    private BooleanExpression goeStartDateNowLoeEndDateNow(FilterOptions filterOptions) {
        return Optional.ofNullable(filterOptions)
                .map(FilterOptions::getProgressType)
                .filter(ON::equals)
                .map(it -> itemEntity.startDate.goe(LocalDate.now()).and(itemEntity.endDate.loe(LocalDate.now())))
                .orElse(null);
    }
}
