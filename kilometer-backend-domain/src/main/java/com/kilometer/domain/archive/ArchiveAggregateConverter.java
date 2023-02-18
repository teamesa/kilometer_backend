package com.kilometer.domain.archive;

import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.dto.ArchiveDetailDto;
import com.kilometer.domain.archive.dto.ArchiveDetailResponse;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.ItemArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveDto;
import com.kilometer.domain.archive.dto.MyArchiveInfo;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import com.kilometer.domain.badge.ItemBadge;
import com.kilometer.domain.badge.ItemBadgeGenerator;
import com.kilometer.domain.item.dto.ItemForArchive;
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
                                          List<ArchiveImageEntity> archiveImageEntities, List<UserVisitPlaceEntity> userVisitPlaceEntities) {

        Map<PlaceType, String> placeTypes = convertFoodAndCafe(userVisitPlaceEntities);

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
            .photoUrls(archiveImageEntities.stream()
                .map(ArchiveImageEntity::getImageUrl)
                .collect(Collectors.toList()))
            .build();
    }

    public ArchiveInfo convertArchiveInfo(ArchiveEntity archiveEntity,
                                          List<ArchiveImageEntity> archiveImageEntities,
                                          List<UserVisitPlaceEntity> userVisitPlaceEntities) {

        Map<PlaceType, String> placeTypes = convertFoodAndCafe(userVisitPlaceEntities);

        ArchiveLike archiveLike = archiveLikeGenerator.generateArchiveLike(archiveEntity.getId());

        List<String> photoUrls = archiveImageEntities.stream()
            .map(ArchiveImageEntity::getImageUrl)
            .collect(Collectors.toList());

        return ArchiveInfo.builder()
            .id(archiveEntity.getId())
            .userProfileUrl(archiveEntity.getUser().getImageUrl())
            .userName(archiveEntity.getUser().getName())
            .updatedAt(archiveEntity.getUpdatedAt())
            .starRating(archiveEntity.getStarRating())
            .likeCount(archiveEntity.getLikeCount())
            .heart(archiveLike)
            .comment(archiveEntity.getComment())
            .food(placeTypes.getOrDefault(PlaceType.FOOD, ""))
            .cafe(placeTypes.getOrDefault(PlaceType.CAFE, ""))
            .photoUrls(photoUrls)
            .build();
    }

    public ArchiveInfo convertArchiveInfo(ArchiveEntity archiveEntity, UserResponse userResponse,
                                          List<ArchiveImageEntity> archiveImageEntities, List<UserVisitPlaceEntity> userVisitPlaceEntities) {

        Map<PlaceType, String> placeTypes = convertFoodAndCafe(userVisitPlaceEntities);

        ArchiveLike archiveLike = archiveLikeGenerator.generateArchiveLike(archiveEntity.getId());

        List<String> photoUrls = archiveImageEntities.stream()
            .map(ArchiveImageEntity::getImageUrl)
            .collect(Collectors.toList());
        return ArchiveInfo.builder()
            .id(archiveEntity.getId())
            .userProfileUrl(userResponse.getImageUrl())
            .userName(userResponse.getName())
            .updatedAt(archiveEntity.getUpdatedAt())
            .starRating(archiveEntity.getStarRating())
            .likeCount(archiveEntity.getLikeCount())
            .heart(archiveLike)
            .comment(archiveEntity.getComment())
            .food(placeTypes.getOrDefault(PlaceType.FOOD, ""))
            .cafe(placeTypes.getOrDefault(PlaceType.CAFE, ""))
            .photoUrls(photoUrls)
            .build();
    }

    public MyArchiveInfo convertMyArchiveInfo(MyArchiveDto myArchiveDto,
        boolean existImages, List<UserVisitPlaceEntity> userVisitPlaceEntities) {

        ItemBadge itemBadge = itemBadgeGenerator.generateTypeItemBadge(ExhibitionType.EXHIBITION);

        return MyArchiveInfo.builder()
            .title(myArchiveDto.getTitle())
            .comment(myArchiveDto.getComment())
            .places(convertVisitPlaces(userVisitPlaceEntities))
            .listImageUrl(myArchiveDto.getListImageUrl())
            .typeBadge(itemBadge)
            .updatedAt(myArchiveDto.getUpdatedAt())
            .existArchiveImages(existImages)
            .api(ApiUrlUtils.getArchiveUrl(myArchiveDto.getId()))
            .build();
    }

    public ArchiveDetailResponse convertArchiveDetail(ArchiveDetailDto archiveDetailDto,
                                                      List<UserVisitPlaceEntity> visitPlaces, List<ArchiveImageEntity> archiveImageEntities) {
        ItemBadge itemBadge = itemBadgeGenerator.generateTypeItemBadge(
            archiveDetailDto.getItemExhibitionType());
        Map<PlaceType, String> placeTypes = convertFoodAndCafe(visitPlaces);
        List<LinkInfo> linkInfos = makeArchiveControlLink(archiveDetailDto.getId(),
            archiveDetailDto.isWrited());
        ItemForArchive itemForArchive = ItemForArchive.of(
            archiveDetailDto.getItemId(),
            archiveDetailDto.getItemTitle(),
            archiveDetailDto.getItemListImageUrl(),
            itemBadge
        );
        return ArchiveDetailResponse.builder()
            .updatedAt(archiveDetailDto.getUpdatedAt())
            .item(itemForArchive)
            .comment(archiveDetailDto.getComment())
            .starRating(archiveDetailDto.getStarRating())
            .food(placeTypes.getOrDefault(PlaceType.FOOD, ""))
            .cafe(placeTypes.getOrDefault(PlaceType.CAFE, ""))
            .photoUrls(
                archiveImageEntities.stream().map(ArchiveImageEntity::getImageUrl).collect(Collectors.toList()))
            .archiveActionButton(linkInfos)
            .visibleAtItem(archiveDetailDto.isVisibleAtItem())
            .build();
    }

    private Map<PlaceType, String> convertFoodAndCafe(List<UserVisitPlaceEntity> userVisitPlaceEntities) {
        return userVisitPlaceEntities.stream()
            .collect(Collectors.toMap(UserVisitPlaceEntity::getPlaceType, UserVisitPlaceEntity::getPlaceName));
    }

    private String convertVisitPlaces(List<UserVisitPlaceEntity> userVisitPlaceEntities) {
        StringBuilder places = new StringBuilder();
        userVisitPlaceEntities.forEach(userVisitPlace -> {
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
