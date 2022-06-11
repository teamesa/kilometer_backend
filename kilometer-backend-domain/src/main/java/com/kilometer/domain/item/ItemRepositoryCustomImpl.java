package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.pick.QPick;
import com.kilometer.domain.search.dto.AutoCompleteItem;
import com.kilometer.domain.search.dto.ListQueryRequest;
import com.kilometer.domain.search.request.FilterOptions;
import com.kilometer.domain.search.request.ProgressDateType;
import com.kilometer.domain.search.request.SearchSortType;
import com.querydsl.core.types.OrderSpecifier;
import com.kilometer.domain.util.FrontUrlUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("rawtypes")
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final static QItemEntity itemEntity = QItemEntity.itemEntity;
    private final static QPick pick = QPick.pick;

    public ItemRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<SearchItemResponse> findAllBySortOption(ListQueryRequest queryRequest) {
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
            .on(pick.pickedItem.eq(itemEntity), eqUserId(queryRequest.getUserId()))
            .where(
                itemEntity.exposureType.eq(ExposureType.ON),
                eqTitle(queryRequest.getQueryString()),
                eqExhibitionType(queryRequest.getFilterOptions()),
                eqFeeType(queryRequest.getFilterOptions()),
                eqRegionType(queryRequest.getFilterOptions()),
                eqProgressType(queryRequest.getFilterOptions())
            )
            .offset(queryRequest.getPageable().getOffset())
            .limit(queryRequest.getPageable().getPageSize())
            .orderBy(getOrderSpecifier(queryRequest.getSearchSortType()))
            .fetch();

        int count = queryFactory
            .select(itemEntity.id)
            .from(itemEntity)
            .where(
                itemEntity.exposureType.eq(ExposureType.ON),
                eqTitle(queryRequest.getQueryString()),
                eqExhibitionType(queryRequest.getFilterOptions()),
                eqFeeType(queryRequest.getFilterOptions()),
                eqRegionType(queryRequest.getFilterOptions()),
                eqProgressType(queryRequest.getFilterOptions())
            )
            .fetch().size();

        return new PageImpl<>(items, queryRequest.getPageable(), count);
    }

    @Override
    public Page<AutoCompleteItem> findTop10ByQuery(String query) {
        List<AutoCompleteItem> items = queryFactory.select(
                Projections.fields(AutoCompleteItem.class,
                    itemEntity.id,
                    itemEntity.title,
                    itemEntity.title.indexOf(query).as("searchedTextLocationStart"),
                    itemEntity.title.indexOf(query).add(query.length()).as("searchedTextLocationEnd"),
                    Expressions.asString(FrontUrlUtils.getFrontDetailPrefix())
                        .append(itemEntity.id.stringValue()).as("link")
                )
            )
            .where(itemEntity.title.containsIgnoreCase(query))
            .from(itemEntity)
            .orderBy(itemEntity.id.desc())
            .limit(10)
            .fetch();

        return new PageImpl<>(items);
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

    private BooleanExpression eqTitle(String query) {
        return Optional.ofNullable(query)
            .filter(StringUtils::isNotBlank)
            .map(it -> itemEntity.title.containsIgnoreCase(query))
            .orElse(null);
    }

    private OrderSpecifier getOrderSpecifier(SearchSortType searchSortType) {
        switch (searchSortType) {
            case END_DATE_ASC:
                return itemEntity.endDate.asc();
            case GRADE_DESC:
            case HEART_DESC:
            case ENROLL_DESC:
            default:
                return itemEntity.id.desc();
        }
    }
}
