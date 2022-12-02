package com.kilometer.domain.homeModules.modules.swipeItem;

import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import com.kilometer.domain.homeModules.modules.swipeItem.dto.SwipeItemDataDto;
import com.kilometer.domain.homeModules.modules.swipeItem.dto.SwipeItemDto;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.util.FrontUrlUtils;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
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
        Preconditions.notNull(paramDto.getModuleDto(), "module must not be null");
        ModuleDto moduleDto = paramDto.getModuleDto();
        Preconditions.notNull(moduleDto.getExtraData(), "Extra_data must not be null");
        long itemId = Long.parseLong(moduleDto.getExtraData());
        SwipeItemDto itemEntity = itemRepository.findSwipeItemByItemId(itemId);
        return SwipeItemDataDto.of(
            FrontUrlUtils.getFrontDetailUrlPattern(itemId),
            itemEntity.getTitle(),
            itemEntity.getContent(),
            itemEntity.getThumbnailImageUrl(),
            itemEntity.getPhotos(),
            SwipeItemKeywordGenerator.generator(itemEntity.getExhibitionType(),
                itemEntity.getPlaceName()));
    }
}
