package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.pick.QPick;
import com.kilometer.domain.search.dto.AutoCompleteItem;
import com.kilometer.domain.search.request.FilterOptions;
import com.kilometer.domain.util.FrontUrlUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
                        loeStartDateNow(filterOptions),
                        goeEndDateNow(filterOptions),
                        goeStartDateNowLoeEndDateNow(filterOptions)
                )
                .fetch();

        return new PageImpl<>(items, pageable, items.size());
    }

    @Override
    public List<AutoCompleteItem> findTop10ByQuery(String query) {
        return queryFactory.select(
                        Projections.fields(AutoCompleteItem.class,
                                itemEntity.id,
                                itemEntity.title,
                                itemEntity.title.locate(query),
                                itemEntity.title.locate(query).add(query.length()),
                                Expressions.asString(FrontUrlUtils.getFrontDetailPrefix()).append(itemEntity.id.stringValue())
                        )
                )
                .where(itemEntity.title.containsIgnoreCase(query))
                .from(itemEntity)
                .orderBy(itemEntity.id.desc())
                .limit(10)
                .fetch();
    }

    private BooleanExpression eqUserId(long userId) {
        if (-1L == userId) {
            return pick.pickedUser.id.eq(0L);
        } else {
            return pick.pickedUser.id.eq(userId);
        }
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
