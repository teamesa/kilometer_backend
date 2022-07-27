package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveDetailDto;
import com.kilometer.domain.archive.dto.ArchiveQueryRequest;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveDto;
import com.kilometer.domain.item.QItemEntity;
import com.kilometer.domain.like.QLike;
import com.kilometer.domain.user.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    private final static QItemEntity itemEntity = QItemEntity.itemEntity;
    private final static QLike like = QLike.like;

    public ArchiveRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<ItemArchiveDto> findAllByItemIdAndUserId(Pageable pageable,
        ArchiveQueryRequest queryRequest) {
        List<ItemArchiveDto> archives = queryFactory
            .select(Projections.fields(ItemArchiveDto.class,
                    archive.id,
                    user.name,
                    user.imageUrl,
                    archive.updatedAt,
                    archive.starRating,
                    archive.likeCount,
                    archive.comment,
                    like.isLiked
                )
            )
            .from(archive)
            .leftJoin(user)
            .on(user.id.eq(archive.user.id))
            .leftJoin(like)
            .on(like.likedArchive.eq(archive), eqUserId(queryRequest.getUserId()))
            .where(
                archive.item.id.eq(queryRequest.getItemId()),
                archive.isVisibleAtItem.eq(true)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(getOrderSpecifier(queryRequest.getArchiveSortType()))
            .fetch();

        int count = queryFactory
            .select(archive.id)
            .from(archive)
            .where(
                archive.item.id.eq(queryRequest.getItemId())
            )
            .fetch().size();

        return new PageImpl<>(archives, pageable, count);
    }

    @Override
    public Page<MyArchiveDto> findAllByUserId(Pageable pageable, ArchiveQueryRequest queryRequest) {

        List<MyArchiveDto> archives = queryFactory
            .select(Projections.fields(MyArchiveDto.class,
                archive.id,
                itemEntity.title,
                itemEntity.exhibitionType,
                itemEntity.listImageUrl,
                archive.comment,
                archive.updatedAt
            ))
            .from(archive)
            .leftJoin(itemEntity)
            .on(itemEntity.id.eq(archive.item.id))
            .where(
                archive.user.id.eq(queryRequest.getUserId())
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(getOrderSpecifier(queryRequest.getArchiveSortType()))
            .fetch();

        int count = queryFactory
            .select(archive.id)
            .from(archive)
            .where(
                archive.user.id.eq(queryRequest.getUserId())
            )
            .fetch().size();

        return new PageImpl<>(archives, pageable, count);
    }

    @Override
    public List<ArchiveDetailDto> findAllByArchiveIdAndUserId(long archiveId, long userId) {
        return queryFactory.select(Projections.fields(ArchiveDetailDto.class,
                archive.id,
                itemEntity.exhibitionType,
                archive.updatedAt,
                itemEntity.title,
                archive.comment,
                archive.starRating,
                user.id.eq(userId).as("isWrited")
            ))
            .from(archive)
            .leftJoin(itemEntity)
            .on(itemEntity.id.eq(archive.item.id))
            .leftJoin(user)
            .on(user.id.eq(archive.user.id))
            .where(
                archive.id.eq(archiveId),
                archive.isVisibleAtItem.isTrue()
            )
            .fetch();
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

    @SuppressWarnings("rawtypes")
    private OrderSpecifier getOrderSpecifier(ArchiveSortType sortType) {
        if (sortType == ArchiveSortType.LIKE_DESC) {
            return archive.likeCount.desc();
        }
        return archive.updatedAt.desc();
    }

    private BooleanExpression eqUserId(long userId) {
        if (-1L == userId) {
            return like.likedUser.id.eq(0L);
        } else {
            return like.likedUser.id.eq(userId);
        }
    }
}
