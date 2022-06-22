package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveFetchUser;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.userVisitPlace.QUserVisitPlace;
import com.kilometer.domain.user.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ArchiveRepositoryCustomImpl implements ArchiveRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final static QArchive archive = QArchive.archive;
    private final static QUser user = QUser.user;
    private final static QUserVisitPlace userVisitPlace = QUserVisitPlace.userVisitPlace;

    public ArchiveRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<ArchiveFetchUser> findAllByItemId(Pageable pageable, ArchiveSortType sortType,
        long itemId) {
        List<ArchiveFetchUser> archives = queryFactory
            .select(Projections.fields(ArchiveFetchUser.class,
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
        if (sortType == ArchiveSortType.LIKE_DESC) {
            return archive.heartCount.desc();
        }
        return archive.updatedAt.desc();
    }
}
