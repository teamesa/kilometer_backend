package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.dto.ItemSaveRequest;
import com.kilometer.domain.item.dto.ItemUpdateRequest;
import com.kilometer.domain.item.dto.SummaryResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemDetailRepository itemDetailRepository;

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
            .time(item.getTime())
            .ticketUrl(item.getTicketUrl())
            .build();
        ItemEntity savedItem = itemRepository.save(itemEntity);
    }

    public List<ItemResponse> findItems() {
        return itemRepository.findAll()
            .stream()
            .map(ItemEntity::makeResponse)
            .collect(Collectors.toList());
    }

    public ItemResponse findOne(Long itemId) {
        Preconditions.notNull(itemId, "id must not be null");

        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + itemId));
        return itemEntity.makeResponse();
    }

    public Optional<SummaryResponse> findToSummaryResponseById(Long itemId) {
        Preconditions.notNull(itemId, "id must not be null");

        return itemRepository.findById(itemId).map(ItemEntity::makeSummaryResponse);
    }

    @Transactional
    public void updateItem(Long itemId, ItemUpdateRequest item) {
        Preconditions.notNull(itemId, "id must not be null");
        Preconditions.notNull(item, "item must not be null");

        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + itemId));
        itemEntity.update(item);
    }

    public void deleteItem(Long itemId) {
        Preconditions.notNull(itemId, "id must not be null");
        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + itemId));
        itemRepository.delete(itemEntity);
    }

    public List<ItemEntity> findItemsAndDetail() {
        List<ItemEntity> findItem = itemRepository.findAll();
        return findItem;
    }

}
