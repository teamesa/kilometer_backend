package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.DetailResponse;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.dto.ItemSaveRequest;
import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.item.dto.SummaryResponse;
import com.kilometer.domain.search.dto.AutoCompleteItem;
import com.kilometer.domain.search.dto.ListQueryRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final ItemDetailImageRepository itemDetailImageRepository;

    public void saveItem(ItemSaveRequest request) {
        ItemEntity item = saveItemEntity(request);
        if (StringUtils.hasText(request.getIntroduce())) {
            saveItemDetail(request, item);
        }

        if (!request.getDetailImageUrls().isEmpty()) {
            saveDetailImage(request, item);
        }

    }

    private ItemDetail saveItemDetail(ItemSaveRequest request, ItemEntity item) {
        ItemDetail itemDetail = request.makeItemDetail();
        itemDetail.setItemEntity(item);
        itemDetailRepository.save(itemDetail);
        return itemDetail;
    }

    private void saveDetailImage(ItemSaveRequest request, ItemEntity item) {
        List<ItemDetailImage> itemDetailImages = request.makeItemDetailImage();
        itemDetailImages.forEach(detailImage -> detailImage.setItemEntity(item));

        itemDetailImageRepository.saveAll(itemDetailImages);
    }

    private ItemEntity saveItemEntity(ItemSaveRequest request) {
        return itemRepository.save(request.makeItemEntity());
    }

    public Page<SearchItemResponse> getItemBySearchOptions(ListQueryRequest queryRequest) {
        return itemRepository.findAllBySortOption(queryRequest);
    }

    public Page<AutoCompleteItem> getAutoCompleteItemByQuery(String query) {
        return itemRepository.findTop10ByQuery(query);
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

//    @Transactional
//    public void updateItem(Long itemId, ItemUpdateRequest item) {
//        Preconditions.notNull(itemId, "id must not be null");
//        Preconditions.notNull(item, "item must not be null");
//
//        ItemEntity itemEntity = itemRepository.findById(itemId)
//            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + itemId));
//        updateDetailImage(item, itemEntity);
//        itemEntity.update(item);
//
//    }

//    private void updateDetailImage(ItemUpdateRequest item, ItemEntity itemEntity) {
//        for (int i = 0; i < item.getDeleteImage().size(); i++) {
//            detailImageRepository.deleteById(item.getDeleteImage().get(i));
//        }
//        ItemDetail itemDetail = itemEntity.getItemDetail();
//        //추가로 들어온 이미지 저장
//        for (int i = 0; i < item.getDetailImageUrl().size(); i++) {
//            ItemDetailImage itemDetailImage = ItemDetailImage.builder()
//                .url(item.getDetailImageUrl().get(i))
//                .itemDetailEntity(itemDetail)
//                .build();
//            detailImageRepository.save(itemDetailImage);
//        }
//    }

    public void deleteItem(Long itemId) {
        Preconditions.notNull(itemId, "id must not be null");
        itemRepository.deleteById(itemId);
    }

    public DetailResponse findToDetailResponseById(Long itemId) {
        Preconditions.notNull(itemId, "id must not be null");

        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("Item이 존재하지 않습니다. id=" + itemId));

        return DetailResponse.makeResponse(itemEntity.getItemDetail(),
            itemEntity.getItemDetailImages());
    }
}
