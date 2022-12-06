package com.kilometer.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long>, ItemRepositoryCustom {

    List<ItemEntity> findByIdIn(List<Long> idList);
}
