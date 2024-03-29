package com.kilometer.domain.archive;

import static com.kilometer.domain.archive.QArchiveEntity.archiveEntity;
import static com.kilometer.domain.archive.archiveImage.QArchiveImageEntity.archiveImageEntity;
import static com.kilometer.domain.archive.userVisitPlace.QUserVisitPlaceEntity.userVisitPlaceEntity;

import com.kilometer.domain.archive.dto.ArchiveDetailDto;
import com.kilometer.domain.archive.dto.ArchiveQueryRequest;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveDto;
import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.archive.like.QLike;
import com.kilometer.domain.item.QItemEntity;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.user.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ArchiveRepositoryCustomImpl implements ArchiveRepositoryCustom {

    private static final int MAX_ARCHIVES = 4;

    private final JPAQueryFactory queryFactory;
    private final static QArchiveEntity archive = archiveEntity;
    private final static QUser user = QUser.user;
    private final static QItemEntity itemEntity = QItemEntity.itemEntity;
    private final static QLike like = QLike.like;

    public ArchiveRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<ItemArchiveDto> findAllItemArchiveByArchiveQueryRequest(Pageable pageable,
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
            .on(like.likedArchiveEntity.eq(archive), eqUserId(queryRequest.getUserId()))
            .where(
                archive.item.id.eq(queryRequest.getItemId()),
                eqIsVisible(queryRequest.isVisible())
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(getOrderSpecifier(queryRequest.getArchiveSortType()))
            .fetch();

        int count = queryFactory
            .select(archive.id)
            .from(archive)
            .where(
                archive.item.id.eq(queryRequest.getItemId()),
                eqIsVisible(queryRequest.isVisible())
            )
            .fetch().size();

        return new PageImpl<>(archives, pageable, count);
    }

    @Override
    public Page<MyArchiveDto> findAllMyArchiveByArchiveQueryRequest(Pageable pageable,
                                                                    ArchiveQueryRequest queryRequest) {

        List<MyArchiveDto> archives = queryFactory
            .select(Projections.fields(MyArchiveDto.class,
                archive.id.as("archiveId"),
                itemEntity.id.as("itemId"),
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
    public Optional<ArchiveDetailDto> findByArchiveIdAndUserIdAndIsVisible(long archiveId,
                                                                           long userId,
                                                                           boolean isVisible) {
        return Optional.ofNullable(queryFactory.select(Projections.fields(ArchiveDetailDto.class,
                archive.id,
                itemEntity.id.as("itemId"),
                itemEntity.listImageUrl.as("itemListImageUrl"),
                itemEntity.exhibitionType.as("itemExhibitionType"),
                archive.updatedAt,
                itemEntity.title.as("itemTitle"),
                archive.comment,
                archive.starRating,
                archive.isVisibleAtItem,
                user.id.eq(userId).as("isWrited")
            ))
            .from(archive)
            .leftJoin(itemEntity)
            .on(itemEntity.id.eq(archive.item.id))
            .leftJoin(user)
            .on(user.id.eq(archive.user.id))
            .where(
                archive.id.eq(archiveId),
                eqIsVisible(isVisible)
            )
            .fetchOne());
    }

    @Override
    public Double avgStarRatingByItemId(long itemId) {
        return queryFactory
            .select(
                archive.starRating.avg()
            )
            .from(archive)
            .where(
                archive.item.id.eq(itemId),
                archive.isVisibleAtItem.eq(true)
            )
            .fetchOne();
    }

    @Override
    public List<RealTimeArchiveDto> findRealTimeArchive(long archiveId) {
        return queryFactory.select(
                                Projections.fields(RealTimeArchiveDto.class,
                                        archiveEntity.id.as("archiveId"),
                                        archiveEntity.likeCount,
                                        archiveEntity.starRating,
                                        archiveEntity.updatedAt,
                                        archiveEntity.comment,
                                        archiveImageEntity.imageUrl,
                                        userVisitPlaceEntity.placeName,
                                        itemEntity.title,
                                        itemEntity.id.as("itemId"),
                                        user.imageUrl.as("userImageUrl"),
                                        user.name.as("userName")
                                )
                        )
                        .from(archive)
                        .leftJoin(userVisitPlaceEntity)
                        .on(userVisitPlaceEntity.archiveEntity.eq(archive))
                        .leftJoin(itemEntity)
                        .on(archive.item.eq(itemEntity))
                        .leftJoin(archiveImageEntity)
                        .on(archiveImageEntity.archiveEntity.eq(archive).and(archiveImageEntity.isDeleted.isFalse()))
                        .leftJoin(user)
                        .on(archive.user.eq(user))
                        .where(
                                archive.id.eq(archiveId),
                                itemEntity.exposureType.eq(ExposureType.ON)
                        )
                        .fetch();
    }

    @Override
    public List<ArchiveEntity> findTopFourVisibleAtItemArchivesWithImageUrl() {
       return queryFactory.select(archiveEntity)
               .distinct()
               .from(archive)
               .leftJoin(archiveImageEntity)
               .on(archiveImageEntity.archiveEntity.eq(archive))
               .where(archiveImageEntity.isNotNull(),
                       archiveEntity.isVisibleAtItem.isTrue())
               .orderBy(archiveEntity.updatedAt.desc())
               .limit(MAX_ARCHIVES)
               .fetch();
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

    private BooleanExpression eqIsVisible(boolean isVisible) {
        if (!isVisible) {
            return null;
        }
        return archive.isVisibleAtItem.eq(true);
    }
}
