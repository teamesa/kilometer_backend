package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemInfoAdditionalInfo;
import com.kilometer.domain.item.dto.ItemInfoDto;
import com.kilometer.domain.item.dto.ItemInfoResponse;
import com.kilometer.domain.item.heart.ItemHeartGenerator;
import com.kilometer.domain.util.ApiUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemAggregateConverter {

    private final ItemHeartGenerator itemHeartGenerator;

    public ItemInfoResponse convert(ItemInfoDto itemInfoDto) {

        return ItemInfoResponse.builder()
            .type(itemInfoDto.getExhibitionType().getDescription())
            .feeType(itemInfoDto.getFeeType().getDescription())
            .title(itemInfoDto.getTitle())
            .term(itemInfoDto.getStartDate() + " ~ " + itemInfoDto.getEndDate())
            .place(itemInfoDto.getPlaceName())
            .lat(itemInfoDto.getLatitude())
            .lng(itemInfoDto.getLongitude())
            .price(itemInfoDto.getPrice())
            .ticketUrl(itemInfoDto.getTicketUrl())
            .time(itemInfoDto.getOperatingTime())
            .homePageUrl(itemInfoDto.getHomePageUrl())
            .thumbnailImageUrl(itemInfoDto.getThumbnailImageUrl())
            .itemInfoAdditionalInfo(
                ItemInfoAdditionalInfo.builder()
                    .heartCount(itemInfoDto.getPickCount())
                    .heart(itemHeartGenerator.generateItemHeart(itemInfoDto.getId(),
                        itemInfoDto.isHeart()))
                    .createArchiveUrl(ApiUrlUtils.ARCHIVE_ROOT)
                    .build())
            .build();

    }

}
