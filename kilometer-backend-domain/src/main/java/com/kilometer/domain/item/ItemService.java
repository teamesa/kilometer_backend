package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemListResponse;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.dto.ItemSaveRequest;
import com.kilometer.domain.item.dto.ItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(ItemSaveRequest item) {
        ItemEntity itemEntity = ItemEntity.builder()
                .exhibitionType(item.getExhibitionType())
                .progressType(item.getProgressType())
                .image(item.getImage())
                .title(item.getTitle())
                .startDate(item.getStartDate())
                .endDate(item.getEndDate())
                .place(item.getPlace())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .regionType(item.getRegionType())
                .place(item.getPlace())
                .fee(item.getFee())
                .price(item.getPrice())
                .url(item.getUrl())
                .build();
        ItemEntity savedItem = itemRepository.save(itemEntity);
    }

    public List<ItemListResponse> findItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemListResponse::new)
                .collect(Collectors.toList());
    }

    public ItemResponse findOne(Long itemId) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + itemId));
        return new ItemResponse(itemEntity);
    }

    @Transactional
    public void updateItem(Long itemId, ItemUpdateRequest item) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + itemId));
        itemEntity.update(item);
    }

    public void deleteItem(Long itemId) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + itemId));
        itemRepository.delete(itemEntity);
    }

}
