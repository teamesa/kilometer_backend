package com.kilometer.domain.archive.dto;

import com.kilometer.domain.archive.PlaceType;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArchiveSelectResult {

    private Long id;
    private String name;
    private String imageUrl;
    private LocalDateTime updatedAt;
    private Integer starRating;
    private Integer heartCount;
    private String comment;
    private final List<UserVisitPlace> places = new ArrayList<>();

    public ArchiveInfo convert() {
        String food = "";
        String cafe = "";

        for (UserVisitPlace place : places) {
            if (PlaceType.CAFE.equals(place.getPlaceType())) {
                cafe = place.getPlaceName();
            } else {
                food = place.getPlaceName();
            }
        }

        return ArchiveInfo.builder()
            .id(this.id)
            .userProfileUrl(imageUrl)
            .userName(name)
            .updatedAt(this.updatedAt)
            .starRating(this.starRating)
            .heartCount(this.heartCount)
            .comment(comment)
            .food(food)
            .cafe(cafe)
            .build();
    }
}
