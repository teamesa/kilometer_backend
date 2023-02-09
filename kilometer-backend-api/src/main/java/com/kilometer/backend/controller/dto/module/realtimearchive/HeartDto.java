package com.kilometer.backend.controller.dto.module.realtimearchive;

import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.util.ApiUrlUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HeartDto {

    private final String link;
    private final boolean heartClicked;

    static HeartDto from(RealTimeArchiveDto realTimeArchiveDto) {
        return HeartDto.builder()
                .link(ApiUrlUtils.getPickItemUrl(realTimeArchiveDto.getItemId()))
                .heartClicked(realTimeArchiveDto.isLiked())
                .build();
    }

    @Override
    public String toString() {
        return "HeartDto{" +
                "link='" + link + '\'' +
                ", heartClicked=" + heartClicked +
                '}';
    }
}
