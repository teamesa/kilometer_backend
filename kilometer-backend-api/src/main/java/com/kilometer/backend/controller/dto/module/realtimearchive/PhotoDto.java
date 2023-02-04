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
public class PhotoDto {

    private String photoUrl;
    private String link;

    static PhotoDto from(RealTimeArchiveDto realTimeArchiveDto) {
        return PhotoDto.builder()
                .photoUrl(realTimeArchiveDto.getImageUrl())
                .link(FrontUrlUtils.getFrontDetailUrlPattern(realTimeArchiveDto.getItemId()))
                .build();
    }

    @Override
    public String toString() {
        return "PhotoDto{" +
                "photoUrl='" + photoUrl + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
