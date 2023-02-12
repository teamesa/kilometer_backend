package com.kilometer.domain.homeModules.modules.swipeItem;

import com.google.common.base.Preconditions;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import com.kilometer.domain.homeModules.modules.swipeItem.dto.SwipeItemDataDto;
import com.kilometer.domain.homeModules.modules.swipeItem.dto.SwipeItemDto;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.exception.ItemExposureOffException;
import com.kilometer.domain.item.exception.ItemNotFoundException;
import com.kilometer.domain.util.FrontUrlUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SwipeItemHandler implements ModuleHandler {

    private final ItemRepository itemRepository;

    @Override
    public boolean supports(ModuleType moduleType) {
        return moduleType == ModuleType.SWIPE_ITEM;
    }

    @Override
    public Object generator(ModuleParamDto paramDto) {
        Preconditions.checkNotNull(paramDto.getModuleDto(), "module must not be null");
        ModuleDto moduleDto = paramDto.getModuleDto();
        Preconditions.checkNotNull(moduleDto.getExtraData(), "Extra_data must not be null");
        long itemId = Long.parseLong(moduleDto.getExtraData());
        SwipeItemDto swipeItem = itemRepository.findSwipeItemByItemId(itemId)
            .orElseThrow(ItemNotFoundException::new);
        if(ExposureType.OFF == swipeItem.getExposureType()) {
            throw new ItemExposureOffException();
        }
        return SwipeItemDataDto.of(
            FrontUrlUtils.getFrontDetailUrlPattern(itemId),
            swipeItem.getTitle(),
            swipeItem.getContent(),
            swipeItem.getThumbnailImageUrl(),
            swipeItem.getPhotos(),
            SwipeItemKeywordGenerator.generator(swipeItem.getExhibitionType(),
                swipeItem.getPlaceName()));
    }
}
