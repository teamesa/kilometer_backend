package com.kilometer.domain.item;

import com.google.common.base.Preconditions;
import com.kilometer.domain.archive.service.ArchiveService;
import com.kilometer.domain.item.domain.Item;
import com.kilometer.domain.item.dto.DetailResponse;
import com.kilometer.domain.item.dto.ItemInfoDto;
import com.kilometer.domain.item.dto.ItemInfoResponse;
import com.kilometer.domain.item.dto.ItemRequest;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.dto.ItemSummaryResponse;
import com.kilometer.domain.item.dto.ItemUpdateRequest;
import com.kilometer.domain.item.dto.ItemUpdateResponse;
import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.exception.ItemExposureOffException;
import com.kilometer.domain.item.exception.ItemNotFoundException;
import com.kilometer.domain.item.itemDetail.ItemDetail;
import com.kilometer.domain.item.itemDetail.ItemDetailRepository;
import com.kilometer.domain.item.itemDetailImage.ItemDetailImage;
import com.kilometer.domain.item.itemDetailImage.ItemDetailImageRepository;
import com.kilometer.domain.search.dto.AutoCompleteItem;
import com.kilometer.domain.search.dto.ListQueryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final ItemDetailImageRepository itemDetailImageRepository;
    private final ItemAggregateConverter itemAggregateConverter;
    private final ArchiveService archiveService;

    @Transactional
    public void saveItem(ItemRequest request, String regAccount) {
        ItemEntity item = saveItemEntity(request, regAccount);

        saveItemDetail(request.makeItemDetail(), item);

        saveDetailImage(request.makeItemDetailImage(), item);
    }

    private void saveItemDetail(ItemDetail itemDetail, ItemEntity item) {
        if (!StringUtils.hasText(itemDetail.getIntroduce())) {
            return;
        }

        itemDetail.setItemEntity(item);
        itemDetailRepository.save(itemDetail);
    }

    private void saveDetailImage(List<ItemDetailImage> itemDetailImages, ItemEntity item) {
        if (itemDetailImages.isEmpty()) {
            return;
        }

        itemDetailImages.forEach(detailImage -> detailImage.setItemEntity(item));

        itemDetailImageRepository.saveAll(itemDetailImages);
    }

    private ItemEntity saveItemEntity(ItemRequest request, String regAccount) {
        return itemRepository.save(request.makeItemEntity(regAccount));
    }

    public Page<SearchItemResponse> getItemBySearchOptions(ListQueryRequest queryRequest) {
        return itemRepository.findAllBySortOption(queryRequest);
    }

    public Page<AutoCompleteItem> getAutoCompleteItemByQuery(String query) {
        return itemRepository.findTop10ByQuery(query);
    }

    public List<ItemResponse> findAll() {
        return itemRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
            .map(ItemEntity::makeResponse)
            .collect(Collectors.toList());
    }

    public Optional<ItemResponse> findOne(Long itemId) {
        Preconditions.checkNotNull(itemId, "id must not be null");

        return itemRepository.findById(itemId).map(ItemEntity::makeResponse);
    }

    // item 만 조회하는 용도로 우선 제작. 다른 메서드들은 모두 Response 형태로 반환하고 있음.
    public Item get(Long itemId) {
        return itemRepository.findById(itemId)
            .map(Item::of)
            .orElseThrow(ItemNotFoundException::new);
    }

    public ItemUpdateResponse findUpdateOne(Long itemId) {
        Preconditions.checkNotNull(itemId, "id must not be null");

        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("해당 Item이 없습니다. id=" + itemId));
        return itemEntity.makeUpdateResponse();
    }

    public ItemInfoResponse getItem(Long itemId, Long userId) {
        Preconditions.checkNotNull(itemId, "id must not be null");

        ItemInfoDto itemInfoDto = itemRepository.findInfoByItemIdAndUserId(itemId, userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 전시글이 없습니다. id = " + itemId));

        if(ExposureType.OFF == itemInfoDto.getExposureType()) {
            throw new ItemExposureOffException();
        }

        // item detail 도메인 객체 empty 반환하도록 리팩토링 필요
        ItemDetail itemDetail = itemDetailRepository.findByItemId(itemId)
            .orElseGet(() -> ItemDetail.builder().introduce("").build());

        List<ItemDetailImage> itemDetailImages = itemDetailImageRepository.findAllByItemId(itemId);

        Long archiveId = archiveService.findArchiveIdByItemIdAndUserId(itemId, userId);

        return itemAggregateConverter.convert(itemInfoDto, itemDetail, itemDetailImages, archiveId);
    }

    // TODO : @Deprecated
    public DetailResponse getItemDetail(Long itemId) {
        Preconditions.checkNotNull(itemId, "id must not be null");

        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("Item이 존재하지 않습니다. id=" + itemId));

        return DetailResponse.makeResponse(itemEntity.getItemDetail(),
            itemEntity.getItemDetailImages());
    }

    @Transactional
    public void updateEditItem(Long itemId, ItemUpdateRequest request, String udtAccount) {
        Preconditions.checkNotNull(itemId, "id must not be null");
        Preconditions.checkNotNull(request, "item must not be null");

        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("해당 전시글이 없습니다. id=" + itemId));

        itemEntity.update(request, udtAccount);
        updateItemDetail(request, itemEntity);
        updateItemDetailImage(request, itemEntity);
    }

    private void deleteDetailImages(List<Long> imageIndex) {
        itemDetailImageRepository.deleteAllById(imageIndex);
    }

    private void deleteItemDetail(ItemDetail itemDetail) {
        itemDetailRepository.delete(itemDetail);
    }

    private void updateItemDetailImage(ItemUpdateRequest request, ItemEntity item) {
        deleteDetailImages(request.getDeleteDetailImages());
        saveDetailImage(request.makeUpdateItemDetailImage(), item);
    }

    private void updateItemDetail(ItemUpdateRequest request, ItemEntity item) {
        ItemDetail itemDetail = item.getItemDetail();
        if (itemDetail != null) {
            if (StringUtils.hasText(request.getIntroduce())) {
                itemDetail.update(request.getIntroduce());
            } else {
                deleteItemDetail(itemDetail);
            }
        } else {
            if (StringUtils.hasText(request.getIntroduce())) {
                saveItemDetail(request.makeUpdateItemDetail(), item);
            }
        }
    }

    @Transactional
    public void deleteItem(Long itemId) {
        Preconditions.checkNotNull(itemId, "id must not be null");
        itemRepository.deleteById(itemId);
    }

    public void plusItemPickCount(Long itemId) {
        Preconditions.checkNotNull(itemId, "id must not be null");

        updateAndDoFunction(ItemEntity::plusPickCount, itemId);
    }

    public void minusItemPickCount(Long itemId) {
        Preconditions.checkNotNull(itemId, "id must not be null");

        updateAndDoFunction(ItemEntity::minusPickCount, itemId);
    }

    private void updateAndDoFunction(Function<ItemEntity, ItemEntity> calculationPickCountIsExposureOn, long itemId) {
        Function<Long, ItemEntity> generated = it -> itemRepository.findById(it)
                .map(calculationPickCountIsExposureOn)
                .map(itemRepository::save)
                .orElseThrow(ItemNotFoundException::new);
        generated.apply(itemId);
    }

    public ItemSummaryResponse getItemSummary(Long itemId, Long userId) {
        return itemRepository.findSummaryByItemIdAndUserId(itemId, userId)
            .map(ItemSummaryResponse::from)
            .orElseThrow(ItemNotFoundException::new);
    }
}
