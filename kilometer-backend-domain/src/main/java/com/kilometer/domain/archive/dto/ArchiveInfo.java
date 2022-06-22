package com.kilometer.domain.archive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kilometer.domain.archive.PlaceType;
import com.kilometer.domain.archive.archiveImage.ArchiveImage;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveInfo {

    private Long id;
    private String userProfileUrl;
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt;
    private Integer starRating;
    private Integer heartCount;
    @Builder.Default
    private Boolean isHearted = false;
    private String comment;
    private String food;
    private String cafe;
    @Builder.Default
    private List<String> photoUrls = List.of();

    public static ArchiveInfo makeInfo(ArchiveFetchUser archiveFetchUser,
        List<ArchiveImage> archiveImages, List<UserVisitPlace> userVisitPlaces) {

        String food = "";
        String cafe = "";

        for (UserVisitPlace userVisitPlace : userVisitPlaces) {
            if (PlaceType.FOOD.equals(userVisitPlace.getPlaceType())) {
                food = userVisitPlace.getPlaceName();
            } else if (PlaceType.CAFE.equals(userVisitPlace.getPlaceType())) {
                cafe = userVisitPlace.getPlaceName();
            }
        }

        return ArchiveInfo.builder()
            .id(archiveFetchUser.getId())
            .userName(archiveFetchUser.getName())
            .userProfileUrl(archiveFetchUser.getImageUrl())
            .updatedAt(archiveFetchUser.getUpdatedAt())
            .starRating(archiveFetchUser.getStarRating())
            .heartCount(archiveFetchUser.getHeartCount())
            .isHearted(archiveFetchUser.getIsHeart())
            .comment(archiveFetchUser.getComment())
            .food(food)
            .cafe(cafe)
            .photoUrls(archiveImages.stream()
                .map(ArchiveImage::getImageUrl)
                .collect(Collectors.toList()))
            .build();
    }
}
