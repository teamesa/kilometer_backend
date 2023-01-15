package com.kilometer.domain.archive;

import com.kilometer.domain.archive.archiveImage.ArchiveImage;
import com.kilometer.domain.archive.dto.ArchiveDetailDto;
import com.kilometer.domain.archive.dto.ArchiveDetailResponse;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveInfo;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.badge.ItemBadge;
import com.kilometer.domain.badge.ItemBadgeGenerator;
import com.kilometer.domain.item.dto.ItemSummary;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.archive.like.dto.ArchiveLike;
import com.kilometer.domain.archive.like.dto.ArchiveLikeGenerator;
import com.kilometer.domain.linkInfo.LinkInfo;
import com.kilometer.domain.user.dto.UserResponse;
import com.kilometer.domain.util.ApiUrlUtils;
import com.kilometer.domain.util.FrontUrlUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArchiveAggregateConverter {

    private final ArchiveLikeGenerator archiveLikeGenerator;
    private final ItemBadgeGenerator itemBadgeGenerator;

    public ArchiveInfo convertArchiveInfo(ItemArchiveDto itemArchiveDto,
        List<ArchiveImage> archiveImages, List<UserVisitPlace> userVisitPlaces) {

        Map<PlaceType, String> placeTypes = convertFoodAndCafe(userVisitPlaces);

        ArchiveLike archiveLike = archiveLikeGenerator.generateArchiveLike(
            itemArchiveDto);

        return ArchiveInfo.builder()
            .id(itemArchiveDto.getId())
            .userName(itemArchiveDto.getName())
            .userProfileUrl(itemArchiveDto.getImageUrl())
            .updatedAt(itemArchiveDto.getUpdatedAt())
            .starRating(itemArchiveDto.getStarRating())
            .likeCount(itemArchiveDto.getLikeCount())
            .heart(archiveLike)
            .comment(itemArchiveDto.getComment())
            .food(placeTypes.getOrDefault(PlaceType.FOOD, ""))
            .cafe(placeTypes.getOrDefault(PlaceType.CAFE, ""))
            .photoUrls(archiveImages.stream()
                .map(ArchiveImage::getImageUrl)
                .collect(Collectors.toList()))
            .build();
    }

    public ArchiveInfo convertArchiveInfo(Archive archive,
        List<ArchiveImage> archiveImages,
        List<UserVisitPlace> userVisitPlaces) {

        Map<PlaceType, String> placeTypes = convertFoodAndCafe(userVisitPlaces);

        ArchiveLike archiveLike = archiveLikeGenerator.generateArchiveLike(archive.getId());

        List<String> photoUrls = archiveImages.stream()
            .map(ArchiveImage::getImageUrl)
            .collect(Collectors.toList());

        return ArchiveInfo.builder()
            .id(archive.getId())
            .userProfileUrl(archive.getUser().getImageUrl())
            .userName(archive.getUser().getName())
            .updatedAt(archive.getUpdatedAt())
            .starRating(archive.getStarRating())
            .likeCount(archive.getLikeCount())
            .heart(archiveLike)
            .comment(archive.getComment())
            .food(placeTypes.getOrDefault(PlaceType.FOOD, ""))
            .cafe(placeTypes.getOrDefault(PlaceType.CAFE, ""))
            .photoUrls(photoUrls)
            .build();
    }

    public ArchiveInfo convertArchiveInfo(Archive archive, UserResponse userResponse,
        List<ArchiveImage> archiveImages, List<UserVisitPlace> userVisitPlaces) {

        Map<PlaceType, String> placeTypes = convertFoodAndCafe(userVisitPlaces);

        ArchiveLike archiveLike = archiveLikeGenerator.generateArchiveLike(archive.getId());

        List<String> photoUrls = archiveImages.stream()
            .map(ArchiveImage::getImageUrl)
            .collect(Collectors.toList());
        return ArchiveInfo.builder()
            .id(archive.getId())
            .userProfileUrl(userResponse.getImageUrl())
            .userName(userResponse.getName())
            .updatedAt(archive.getUpdatedAt())
            .starRating(archive.getStarRating())
            .likeCount(archive.getLikeCount())
            .heart(archiveLike)
            .comment(archive.getComment())
            .food(placeTypes.getOrDefault(PlaceType.FOOD, ""))
            .cafe(placeTypes.getOrDefault(PlaceType.CAFE, ""))
            .photoUrls(photoUrls)
            .build();
    }

    public MyArchiveInfo convertMyArchiveInfo(MyArchiveDto myArchiveDto,
        boolean existImages, List<UserVisitPlace> userVisitPlaces) {

        ItemBadge itemBadge = itemBadgeGenerator.generateTypeItemBadge(ExhibitionType.EXHIBITION);

        return MyArchiveInfo.builder()
            .title(myArchiveDto.getTitle())
            .comment(myArchiveDto.getComment())
            .places(convertVisitPlaces(userVisitPlaces))
            .listImageUrl(myArchiveDto.getListImageUrl())
            .typeBadge(itemBadge)
            .updatedAt(myArchiveDto.getUpdatedAt())
            .existArchiveImages(existImages)
            .api(ApiUrlUtils.getArchiveDetailUrl(myArchiveDto.getId()))
            .build();
    }

    public ArchiveDetailResponse convertArchiveDetail(ArchiveDetailDto archiveDetailDto,
        List<UserVisitPlace> visitPlaces, List<ArchiveImage> archiveImages) {
        ItemBadge itemBadge = itemBadgeGenerator.generateTypeItemBadge(
            archiveDetailDto.getItemExhibitionType());
        Map<PlaceType, String> placeTypes = convertFoodAndCafe(visitPlaces);
        List<LinkInfo> linkInfos = makeArchiveControlLink(archiveDetailDto.getId(),
            archiveDetailDto.isWrited());
        ItemSummary itemSummary = ItemSummary.of(
            archiveDetailDto.getId(),
            archiveDetailDto.getItemTitle(),
            archiveDetailDto.getItemListImageUrl(),
            itemBadge
        );
        return ArchiveDetailResponse.builder()
            .updatedAt(archiveDetailDto.getUpdatedAt())
            .item(itemSummary)
            .comment(archiveDetailDto.getComment())
            .starRating(archiveDetailDto.getStarRating())
            .food(placeTypes.getOrDefault(PlaceType.FOOD, ""))
            .cafe(placeTypes.getOrDefault(PlaceType.CAFE, ""))
            .photoUrls(
                archiveImages.stream().map(ArchiveImage::getImageUrl).collect(Collectors.toList()))
            .archiveActionButton(linkInfos)
            .visibleAtItem(archiveDetailDto.isVisibleAtItem())
            .build();
    }

    private Map<PlaceType, String> convertFoodAndCafe(List<UserVisitPlace> userVisitPlaces) {
        return userVisitPlaces.stream()
            .collect(Collectors.toMap(UserVisitPlace::getPlaceType, UserVisitPlace::getPlaceName));
    }

    private String convertVisitPlaces(List<UserVisitPlace> userVisitPlaces) {
        StringBuilder places = new StringBuilder();
        userVisitPlaces.forEach(userVisitPlace -> {
            if (places.length() != 0) {
                places.append(", ");
            }
            places.append(userVisitPlace.getPlaceName());
        });

        return (places.length() == 0) ? null : places.toString();
    }

    private List<LinkInfo> makeArchiveControlLink(Long archiveId, boolean isWriter) {
        if (!isWriter) {
            return List.of();
        }

        return List.of(
            LinkInfo.of("수정", FrontUrlUtils.getFrontModifyArchiveWithId(archiveId)),
            LinkInfo.of("삭제", ApiUrlUtils.getArchiveUrl(archiveId)));

    }
}
