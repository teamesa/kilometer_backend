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
    private final AdditionalInformationDto additionalInformation;
    private final InformationDto information;

    public static ArchiveDto from(RealTimeArchiveDto realTimeArchiveDto) {
        return ArchiveDto.builder()
                .photo(PhotoDto.from(realTimeArchiveDto))
                .additionalInformation(AdditionalInformationDto.from(realTimeArchiveDto))
                .information(InformationDto.from(realTimeArchiveDto))
                .build();
    }

    @Override
    public String toString() {
        return "ArchiveDto{" +
                "photo=" + photo +
                ", additionalInformation=" + additionalInformation +
                ", information=" + information +
                '}';
    }
}
