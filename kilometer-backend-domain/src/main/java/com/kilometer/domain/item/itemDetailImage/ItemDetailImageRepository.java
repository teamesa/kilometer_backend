package com.kilometer.domain.item.itemDetailImage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDetailImageRepository extends JpaRepository<ItemDetailImage, Long> {
    List<ItemDetailImage> findAllByItemId(Long itemId);
}
