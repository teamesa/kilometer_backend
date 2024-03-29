package com.kilometer.backend.controller.dto.module.realtimearchive;

import com.kilometer.backend.util.StringConvertor;
import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IntroductionDto {

    private final String places;
    private final String comment;
    private final TitleDto title;

    static IntroductionDto from(RealTimeArchiveDto realTimeArchiveDto) {
        return IntroductionDto.builder()
                .places(StringConvertor.convertNullToBlank(realTimeArchiveDto.getPlaceName()))
                .comment(StringConvertor.convertNullToBlank(realTimeArchiveDto.getComment()))
                .title(TitleDto.from(realTimeArchiveDto))
                .build();
    }

    @Override
    public String toString() {
        return "Information{" +
                "places='" + places + '\'' +
                ", comment='" + comment + '\'' +
                ", title=" + title +
                '}';
    }
}
