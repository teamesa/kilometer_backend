package com.kilometer.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long>, ItemRepositoryCustom {
}
