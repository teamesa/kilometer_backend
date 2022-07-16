package com.kilometer.domain.item;

import com.kilometer.domain.archive.QArchive;
import com.kilometer.domain.item.dto.ItemInfoDto;
import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.pick.QPick;
import com.kilometer.domain.search.dto.AutoCompleteItem;
import com.kilometer.domain.search.dto.ListQueryRequest;
import com.kilometer.domain.search.request.FilterOptions;
import com.kilometer.domain.search.request.ProgressDateType;
import com.kilometer.domain.search.request.SearchSortType;
import com.kilometer.domain.util.FrontUrlUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@SuppressWarnings("rawtypes")
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final static QItemEntity itemEntity = QItemEntity.itemEntity;
    private final static QPick pick = QPick.pick;
    private final static QArchive archive = QArchive.archive;

    public ItemRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<SearchItemResponse> findAllBySortOption(ListQueryRequest queryRequest) {
        List<SearchItemResponse> items = queryFactory
            .select(Projections.fields(SearchItemResponse.class,
                    itemEntity.id,
                    itemEntity.listImageUrl,
                    itemEntity.title,
                    itemEntity.exhibitionType,
                    itemEntity.feeType,
                    itemEntity.startDate,
                    itemEntity.endDate,
                    itemEntity.pickCount,
                    pick.isHearted,
                    archive.starRating.avg().as("avgStarRating"),
                    archive.item.id.count().as("archiveCount")
                )
            )
            .from(itemEntity)
            .leftJoin(pick)
            .on(pick.pickedItem.eq(itemEntity), eqUserId(queryRequest.getUserId()))
            .leftJoin(archive)
            .on(archive.item.eq(itemEntity))
            .where(
                itemEntity.exposureType.eq(ExposureType.ON),
                archive.isVisibleAtItem.eq(true),
                eqTitle(queryRequest.getQueryString()),
                eqExhibitionType(queryRequest.getFilterOptions()),
                eqFeeType(queryRequest.getFilterOptions()),
                eqRegionType(queryRequest.getFilterOptions()),
                eqProgressType(queryRequest.getFilterOptions()),
                filterByOrderOption(queryRequest.getSearchSortType())
            )
            .offset(queryRequest.getPageable().getOffset())
            .limit(queryRequest.getPageable().getPageSize())
            .groupBy(itemEntity.id)
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

    @Override
    public Optional<ItemInfoDto> findInfoByItemIdAndUserId(Long itemId, Long userId) {
        return Optional.ofNullable(queryFactory.select(
                Projections.fields(ItemInfoDto.class,
                    itemEntity.id,
                    itemEntity.exhibitionType,
                    itemEntity.feeType,
                    itemEntity.title,
                    itemEntity.thumbnailImageUrl,
                    itemEntity.startDate,
                    itemEntity.endDate,
                    itemEntity.placeName,
                    itemEntity.latitude,
                    itemEntity.longitude,
                    itemEntity.price,
                    itemEntity.ticketUrl,
                    itemEntity.operatingTime,
                    itemEntity.homepageUrl,
                    itemEntity.pickCount,
                    pick.isHearted
                )
            )
            .from(itemEntity)
            .leftJoin(pick)
            .on(pick.pickedItem.eq(itemEntity), eqUserId(userId))
            .where(itemEntity.id.eq(itemId))
            .fetchOne());
    }

    private BooleanExpression eqExhibitionType(FilterOptions filterOptions) {
        return Optional.ofNullable(filterOptions)
            .map(FilterOptions::getExhibitionType)
            .filter(exhibitionType -> exhibitionType != ExhibitionType.ALL)
            .map(itemEntity.exhibitionType::eq)
            .orElse(null);
    }

    private BooleanExpression eqFeeType(FilterOptions filterOptions) {
        return Optional.ofNullable(filterOptions)
            .map(FilterOptions::getFeeTypes)
            .map(Collection::stream)
            .map(it -> it.map(itemEntity.feeType::eq))
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
                return archive.starRating.avg().desc();
            case HEART_DESC:
                return itemEntity.pickCount.desc();
            case ENROLL_DESC:
            default:
                return itemEntity.id.desc();
        }
    }

    private Predicate filterByOrderOption(SearchSortType searchSortType) {
        LocalDate now = LocalDate.now();

        if (searchSortType == SearchSortType.END_DATE_ASC) {
            return itemEntity.endDate.goe(now);
        }

        return null;
    }
}
