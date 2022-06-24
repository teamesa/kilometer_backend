package com.kilometer.domain.archive;

import com.kilometer.domain.archive.archiveImage.ArchiveImage;
import com.kilometer.domain.archive.dto.ArchiveFetchUser;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.heart.ArchiveHeart;
import com.kilometer.domain.archive.heart.ArchiveHeartGenerator;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArchiveAggregateConverter {

    private final ArchiveHeartGenerator archiveHeartGenerator;

    public ArchiveInfo convertArchiveInfo(ArchiveFetchUser archiveFetchUser,
        List<ArchiveImage> archiveImages, List<UserVisitPlace> userVisitPlaces) {

        Map<PlaceType, String> placeTypes = convertFoodAndCafe(userVisitPlaces);

        ArchiveHeart archiveHeart = archiveHeartGenerator.generateArchiveHeart(
            archiveFetchUser);

        return ArchiveInfo.builder()
            .id(archiveFetchUser.getId())
            .userName(archiveFetchUser.getName())
            .userProfileUrl(archiveFetchUser.getImageUrl())
            .updatedAt(archiveFetchUser.getUpdatedAt())
            .starRating(archiveFetchUser.getStarRating())
            .heartCount(archiveFetchUser.getHeartCount())
            .heart(archiveHeart)
            .comment(archiveFetchUser.getComment())
            .food(placeTypes.getOrDefault(PlaceType.FOOD,""))
            .cafe(placeTypes.getOrDefault(PlaceType.CAFE,""))
            .photoUrls(archiveImages.stream()
                .map(ArchiveImage::getImageUrl)
                .collect(Collectors.toList()))
            .build();
    }

    public ArchiveInfo convertArchiveInfo(Archive archive) {

        Map<PlaceType, String> placeTypes = convertFoodAndCafe(archive.getUserVisitPlaces());

        ArchiveHeart archiveHeart = archiveHeartGenerator.generateArchiveHeart(archive.getId());

        return ArchiveInfo.builder()
            .id(archive.getId())
            .userProfileUrl(archive.getUser().getImageUrl())
            .userName(archive.getUser().getName())
            .updatedAt(archive.getUpdatedAt())
            .starRating(archive.getStarRating())
            .heartCount(archive.getHeartCount())
            .heart(archiveHeart)
            .comment(archive.getComment())
            .food(placeTypes.getOrDefault(PlaceType.FOOD,""))
            .cafe(placeTypes.getOrDefault(PlaceType.CAFE,""))
            .photoUrls(archive.getArchiveImages().stream()
                .map(ArchiveImage::getImageUrl)
                .collect(Collectors.toList()))
            .build();
    }

    private Map<PlaceType, String> convertFoodAndCafe(List<UserVisitPlace> userVisitPlaces) {
        return userVisitPlaces.stream()
            .collect(Collectors.toMap(UserVisitPlace::getPlaceType, UserVisitPlace::getPlaceName));
    }

}
