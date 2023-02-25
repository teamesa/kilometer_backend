package com.kilometer.domain.item;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long>, ItemRepositoryCustom {

    List<ItemEntity> findByIdIn(List<Long> idList);

    Optional<ItemEntityMapper.ExposureTypeMapping> findExposureById(Long id);
}
