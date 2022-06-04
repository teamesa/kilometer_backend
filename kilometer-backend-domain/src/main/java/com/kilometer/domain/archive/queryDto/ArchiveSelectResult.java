package com.kilometer.domain.archive.queryDto;

import com.kilometer.domain.archive.PlaceType;
import com.kilometer.domain.archive.VisitedPlace;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    private List<VisitedPlace> places = List.of();

    public ArchiveInfo convert() {
        String food = "";
        String cafe = "";

        for (VisitedPlace place : places) {
            if(PlaceType.CAFE == place.getPlaceType()) {
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
