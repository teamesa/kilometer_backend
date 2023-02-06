package com.kilometer.backend.controller.dto.module.realtimearchive;

import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdditionalInformationDto {

    public static final String DIM_COLOR = "#fff";
    public static final String OPACITY = "0.8";

    private final UserDto user;
    private final HeartDto heart;
    private final int likeCount;
    private final int starRating;
    private final String updatedAt;
    private final String dimColor;
    private final String opacity;

    static AdditionalInformationDto from(RealTimeArchiveDto realTimeArchiveDto) {
        return AdditionalInformationDto.builder()
                .user(UserDto.from(realTimeArchiveDto))
                .heart(HeartDto.from(realTimeArchiveDto))
                .likeCount(realTimeArchiveDto.getLikeCount())
                .starRating(realTimeArchiveDto.getStarRating())
                .updatedAt(realTimeArchiveDto.getUpdatedAt().toString())
                .dimColor(DIM_COLOR)
                .opacity(OPACITY)
                .build();
    }

    @Override
    public String toString() {
        return "AdditionalInformationDto{" +
                "user=" + user +
                ", heart=" + heart +
                ", likeCount=" + likeCount +
                ", starRating=" + starRating +
                ", updatedAt='" + updatedAt + '\'' +
                ", dimColor='" + dimColor + '\'' +
                ", opacity='" + opacity + '\'' +
                '}';
    }
}
