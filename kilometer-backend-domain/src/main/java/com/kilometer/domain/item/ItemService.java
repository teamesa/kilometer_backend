package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ItemAndDetailListResponse> findItemsAndDetail() {
        List<ItemEntity> findItem = itemRepository.findAll();
//        List<ItemDetailEntity> findDetailItem = new ArrayList<>();
//        for (ItemEntity itemEntity : findItem) {
//            findDetailItem.add(itemEntity.getItemDetailEntity());
//        }
        List<ItemAndDetailListResponse> result = new ArrayList<>();
        int n = findItem.size();
        for (int i = 0; i < n; i++) {
            result.add(new ItemAndDetailListResponse(findItem.get(i)));
        }
        return result;


//        List<ItemListResponse> items = this.findItems();
//        List<ItemAndDetailListResponse> result = new ArrayList<>();
//        for (ItemListResponse item : items) {
//            ItemDetailEntity findItemDetailEntity = itemDetailRepository.findById(item.getDetailId())
//                    .orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
//            result.add(new ItemAndDetailListResponse(
//                    item.getId(),
//                    item.getExhibitionType(),
//                    item.getProgressType(),
//                    item.getImage(),
//                    item.getTitle(),
//                    item.getStartDate(),
//                    item.getEndDate(),
//                    item.getPlace(),
//                    item.getLatitude(),
//                    item.getLongitude(),
//                    item.getRegionType(),
//                    item.getFee(),
//                    item.getPrice(),
//                    item.getUrl(),
//                    item.getTime(),
//                    item.getTicketUrl(),
//                    findItemDetailEntity
//                    )
//            );
//        }
//        return result;
    }

}
