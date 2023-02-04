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

    private UserDto user;
    private HeartDto heart;
    private int likeCount;
    private int starRating;
    private String updatedAt;
    private String dimColor;
    private String opacity;

    static AdditionalInformationDto from(RealTimeArchiveDto realTimeArchiveDto) {
        return AdditionalInformationDto.builder()
                .user(UserDto.from(realTimeArchiveDto))
                .heart(HeartDto.from(realTimeArchiveDto))
                .likeCount(realTimeArchiveDto.getLikeCount())
                .starRating(realTimeArchiveDto.getStarRating())
                .updatedAt(realTimeArchiveDto.getUpdatedAt().toString())
                .dimColor("#fff")
                .opacity("0.8")
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
