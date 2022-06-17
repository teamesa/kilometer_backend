package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.DetailResponse;
import com.kilometer.domain.item.dto.ItemRequest;
import com.kilometer.domain.item.dto.ItemResponse;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final ItemDetailImageRepository itemDetailImageRepository;

    @Transactional
    public void saveItem(ItemRequest request) {
        ItemEntity item = saveItemEntity(request);

        saveItemDetail(request, item);

        saveDetailImage(request, item);
    }

    private void saveItemDetail(ItemRequest request, ItemEntity item) {
        if (!StringUtils.hasText(request.getIntroduce())) {
            return;
        }

        ItemDetail itemDetail = request.makeItemDetail();
        itemDetail.setItemEntity(item);
        itemDetailRepository.save(itemDetail);
    }

    private void saveDetailImage(ItemRequest request, ItemEntity item) {
        if (request.getDetailImageUrls().isEmpty()) {
            return;
        }

        List<ItemDetailImage> itemDetailImages = request.makeItemDetailImage();
        itemDetailImages.forEach(detailImage -> detailImage.setItemEntity(item));

        itemDetailImageRepository.saveAll(itemDetailImages);
    }

    private ItemEntity saveItemEntity(ItemRequest request) {
        return itemRepository.save(request.makeItemEntity());
    }

    public Page<SearchItemResponse> getItemBySearchOptions(ListQueryRequest queryRequest) {
        return itemRepository.findAllBySortOption(queryRequest);
    }

    public Page<AutoCompleteItem> getAutoCompleteItemByQuery(String query) {
        return itemRepository.findTop10ByQuery(query);
    }

    public List<ItemResponse> findAll() {
        return itemRepository.findAll().stream()
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
    public void updateItem(Long itemId, ItemRequest request) {
        Preconditions.notNull(itemId, "id must not be null");
        Preconditions.notNull(request, "item must not be null");

        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("해당 전시글이 없습니다. id=" + itemId));

        itemEntity.update(request);
        updateItemDetail(request, itemEntity);
        updateItemDetailImage(request, itemEntity);


    }

    private void deleteDetailImages(List<ItemDetailImage> images) {
        itemDetailImageRepository.deleteAll(images);
    }

    private void deleteItemDetail(ItemDetail itemDetail) {
        itemDetailRepository.delete(itemDetail);
    }

    private void updateItemDetailImage(ItemRequest request, ItemEntity item) {
        deleteDetailImages(item.getItemDetailImages());
        saveDetailImage(request, item);
    }

    private void updateItemDetail(ItemRequest request, ItemEntity item) {
        ItemDetail itemDetail = item.getItemDetail();
        if (itemDetail != null) {
            if (StringUtils.hasText(request.getIntroduce())) {
                itemDetail.update(request.getIntroduce());
            } else {
                deleteItemDetail(itemDetail);
            }
        } else {
            if (StringUtils.hasText(request.getIntroduce())) {
                saveItemDetail(request, item);
            }
        }
    }

    @Transactional
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
