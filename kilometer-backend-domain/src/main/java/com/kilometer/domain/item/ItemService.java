package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.DetailResponse;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.dto.ItemSaveRequest;
import com.kilometer.domain.item.dto.ItemUpdateRequest;
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
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final DetailImageRepository detailImageRepository;

    public void saveItem(ItemSaveRequest item) {
        ItemDetail itemDetail = null;
        if(StringUtils.hasText(item.getIntroduce())) {
            itemDetail = saveItemDetail(item);
        }
        saveItemEntity(item, itemDetail);
    }

    private ItemDetail saveItemDetail(ItemSaveRequest item) {

        ItemDetail itemDetail = item.makeItemDetail();
        itemDetailRepository.save(itemDetail);

        saveDetailImage(item, itemDetail);
        return itemDetail;
    }

    private void saveDetailImage(ItemSaveRequest item, ItemDetail itemDetail) {
        List<DetailImage> detailImages = item.getDetailImageUrls().stream()
            .map(url -> DetailImage.makeEntity(url, itemDetail))
            .collect(Collectors.toList());

        itemDetail.setDetailImages(detailImages);
        detailImageRepository.saveAll(detailImages);
    }

    private void saveItemEntity(ItemSaveRequest item, ItemDetail itemDetail) {
        ItemEntity itemEntity = item.makeItemEntity(itemDetail);
        itemRepository.save(itemEntity);
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

    @Transactional
    public void updateItem(Long itemId, ItemUpdateRequest item) {
        Preconditions.notNull(itemId, "id must not be null");
        Preconditions.notNull(item, "item must not be null");

        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + itemId));
        updateDetailImage(item, itemEntity);
        itemEntity.update(item);

    }

    private void updateDetailImage(ItemUpdateRequest item, ItemEntity itemEntity) {
        for (int i = 0; i < item.getDeleteImage().size(); i++) {
            detailImageRepository.deleteById(item.getDeleteImage().get(i));
        }
        ItemDetail itemDetail = itemEntity.getItemDetailEntity();
        //추가로 들어온 이미지 저장
        for (int i = 0; i < item.getDetailImageUrl().size(); i++) {
            DetailImage detailImage = DetailImage.builder()
                .url(item.getDetailImageUrl().get(i))
                .itemDetailEntity(itemDetail)
                .build();
            detailImageRepository.save(detailImage);
        }
    }

    public void deleteItem(Long itemId) {
        Preconditions.notNull(itemId, "id must not be null");
        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + itemId));
        itemRepository.delete(itemEntity);
    }

    public DetailResponse findToDetailResponseById(Long itemId) {
        Preconditions.notNull(itemId, "id must not be null");

        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("Item이 존재하지 않습니다. id=" + itemId));

        return Optional.ofNullable(itemEntity.getItemDetailEntity())
            .map(itemDetailEntity -> itemDetailRepository.findById(itemDetailEntity.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                    "ItemDetail not found id=" + itemDetailEntity.getId()))
            )
            .map(ItemDetail::makeResponse)
            .orElse(DetailResponse.empty());
    }
}
