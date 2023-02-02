package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemInfoAdditionalInfo;
import com.kilometer.domain.item.dto.ItemInfoDto;
import com.kilometer.domain.item.dto.ItemInfoResponse;
import com.kilometer.domain.item.heart.ItemHeartGenerator;
import com.kilometer.domain.item.itemDetail.ItemDetail;
import com.kilometer.domain.item.itemDetailImage.ItemDetailImage;
import com.kilometer.domain.linkInfo.LinkInfo;
import com.kilometer.domain.util.FrontUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemAggregateConverter {

    private final ItemHeartGenerator itemHeartGenerator;

    public ItemInfoResponse convert(ItemInfoDto itemInfoDto, ItemDetail itemDetail,
                                    List<ItemDetailImage> itemDetailImages, Long archiveId) {

        LinkInfo archiveLink;
        if (archiveId != null) {
            archiveLink = LinkInfo.of(
                FrontUrlUtils.getFrontModifyTitle(),
                FrontUrlUtils.getFrontModifyArchiveWithId(archiveId));
        } else {
            archiveLink = LinkInfo.of(
                FrontUrlUtils.getFrontCreateTitle(),
                FrontUrlUtils.getFrontCreateUrl());
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        return ItemInfoResponse.builder()
            .type(itemInfoDto.getExhibitionType().getDescription())
            .feeType(itemInfoDto.getFeeType().getDescription())
            .title(itemInfoDto.getTitle())
            .term(itemInfoDto.getStartDate().format(dateTimeFormatter) + " ~ "
                + itemInfoDto.getEndDate().format(dateTimeFormatter))
            .place(itemInfoDto.getPlaceName())
            .lat(itemInfoDto.getLatitude())
            .lng(itemInfoDto.getLongitude())
            .price(itemInfoDto.getPrice())
            .ticketUrl(itemInfoDto.getTicketUrl())
            .time(itemInfoDto.getOperatingTime())
            .homePageUrl(itemInfoDto.getHomepageUrl())
            .detailImageUrl(itemInfoDto.getThumbnailImageUrl())
            .listImageUrl(itemInfoDto.getListImageUrl())
            .summary(Optional.ofNullable(itemDetail)
                .map(ItemDetail::getIntroduce)
                .orElse(null))
            .photo(Optional.ofNullable(itemDetailImages)
                .map(images -> images.stream()
                    .map(ItemDetailImage::getImageUrl)
                    .collect(Collectors.toList()))
                .orElse(List.of()))
            .itemInfoAdditionalInfo(
                ItemInfoAdditionalInfo.builder()
                    .heartCount(itemInfoDto.getPickCount())
                    .heart(itemHeartGenerator.generateItemHeart(itemInfoDto.getId(),
                        itemInfoDto.isHearted()))
                    .archiveLink(archiveLink)
                    .build())
            .build();

    }
}
