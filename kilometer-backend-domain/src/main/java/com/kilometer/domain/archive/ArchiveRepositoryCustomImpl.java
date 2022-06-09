package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.queryDto.ArchiveSelectResult;
import com.kilometer.domain.user.QUser;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArchiveRepositoryCustomImpl implements ArchiveRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final static QArchive archive = QArchive.archive;
    private final static QUser user = QUser.user;
    private final static QVisitedPlace visitedPlace = QVisitedPlace.visitedPlace;

    public ArchiveRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<ArchiveSelectResult> findAllByItemId(Pageable pageable, ArchiveSortType sortType, long itemId) {
        List<ArchiveSelectResult> archives = queryFactory
                .select(Projections.fields(ArchiveSelectResult.class,
                                archive.id,
                                user.name,
                                user.imageUrl,
                                archive.updatedAt,
                                archive.starRating,
                                archive.heartCount,
                                archive.comment
                        )
                )
                .from(archive)
                .leftJoin(user)
                .on(user.id.eq(archive.user.id))
                .leftJoin(visitedPlace)
                .on(visitedPlace.archive.id.eq(archive.id))
                .where(
                        archive.item.id.eq(itemId),
                        archive.isVisibleAtItem.eq(true)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifier(sortType))
                .fetch();

        int count = queryFactory
                .select(archive.id)
                .from(archive)
                .where(
                        archive.item.id.eq(itemId)
                )
                .fetch().size();

        return new PageImpl<>(archives, pageable, count);
    }

    @Override
    public Double avgStarRatingByItemId(long itemId) {
        return queryFactory
                .select(
                        archive.starRating.avg()
                )
                .from(archive)
                .where(
                        archive.item.id.eq(itemId)
                )
                .fetchOne();
    }

    private OrderSpecifier getOrderSpecifier(ArchiveSortType sortType) {
        if (sortType == ArchiveSortType.LIKE_DESC)
            return archive.heartCount.desc();
        return archive.updatedAt.desc();
    }
}
