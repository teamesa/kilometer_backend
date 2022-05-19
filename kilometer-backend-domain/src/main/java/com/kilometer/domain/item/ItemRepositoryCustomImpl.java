package com.kilometer.domain.item;

import com.kilometer.domain.search.request.FilterOptions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

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
                .fetch();

        return new PageImpl<>(items, pageable, items.size());
    }
}
