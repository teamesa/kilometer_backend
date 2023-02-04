package com.kilometer.backend.controller.dto.module.realtimearchive;

import com.kilometer.backend.util.Convertor;
import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InformationDto {

    private String places;
    private String comment;
    private TitleDto title;

    static InformationDto from(RealTimeArchiveDto realTimeArchiveDto) {
        return InformationDto.builder()
                .places(Convertor.convertNullToBlank(realTimeArchiveDto.getPlaceName()))
                .comment(Convertor.convertNullToBlank(realTimeArchiveDto.getComment()))
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
