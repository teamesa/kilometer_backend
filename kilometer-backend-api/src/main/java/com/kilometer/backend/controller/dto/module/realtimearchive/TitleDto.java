package com.kilometer.backend.controller.dto.module.realtimearchive;

import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.util.FrontUrlUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TitleDto {

    private String link;
    private String value;

    static TitleDto from(RealTimeArchiveDto realTimeArchiveDto) {
        return TitleDto.builder()
                .link(FrontUrlUtils.getFrontDetailUrlPattern(realTimeArchiveDto.getItemId()))
                .value(realTimeArchiveDto.getTitle())
                .build();
    }

    @Override
    public String toString() {
        return "Title{" +
                "link='" + link + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
