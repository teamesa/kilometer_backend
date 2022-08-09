package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemInfoAdditionalInfo;
import com.kilometer.domain.item.dto.ItemInfoDto;
import com.kilometer.domain.item.dto.ItemInfoResponse;
import com.kilometer.domain.item.heart.ItemHeartGenerator;
import com.kilometer.domain.linkInfo.LinkInfo;
import com.kilometer.domain.util.FrontUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemAggregateConverter {

    private final ItemHeartGenerator itemHeartGenerator;

    public ItemInfoResponse convert(ItemInfoDto itemInfoDto, Long archiveId) {

        LinkInfo archiveLink;
        if (archiveId != null) {
            archiveLink = LinkInfo.of(
                FrontUrlUtils.getFrontModifyTitle(),
                FrontUrlUtils.getFrontModifyUrl(archiveId));
        } else {
            archiveLink = LinkInfo.of(
                FrontUrlUtils.getFrontCreateTitle(),
                FrontUrlUtils.getFrontCreateUrl());
        }

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
            .homePageUrl(itemInfoDto.getHomepageUrl())
            .detailImageUrl(itemInfoDto.getThumbnailImageUrl())
            .listImageUrl(itemInfoDto.getListImageUrl())
            .itemInfoAdditionalInfo(
                ItemInfoAdditionalInfo.builder()
                    .heartCount(itemInfoDto.getPickCount())
                    .heart(itemHeartGenerator.generateItemHeart(itemInfoDto.getId(),
                        itemInfoDto.isHeart()))
                    .archiveLink(archiveLink)
                    .build())
            .build();

    }

}
