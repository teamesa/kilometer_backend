package com.kilometer.backend.controller.dto.module.realtimearchive;

import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ArchiveDto {

    private final PhotoDto photo;
    private final MetaDataDto metaData;
    private final IntroductionDto introduction;

    public static ArchiveDto from(RealTimeArchiveDto realTimeArchiveDto) {
        return ArchiveDto.builder()
                .photo(PhotoDto.from(realTimeArchiveDto))
                .metaData(MetaDataDto.from(realTimeArchiveDto))
                .introduction(IntroductionDto.from(realTimeArchiveDto))
                .build();
    }

    @Override
    public String toString() {
        return "ArchiveDto{" +
                "photo=" + photo +
                ", metaData=" + metaData +
                ", introduction=" + introduction +
                '}';
    }
}
