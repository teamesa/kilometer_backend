package com.kilometer.backend.controller.dto.module.realtimearchive;

import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ArchivesDto {

    private List<ArchiveDto> archives;

    public static ArchivesDto from(ModuleResponseDto<List<RealTimeArchiveDto>> moduleResponseDtos) {
        List<ArchiveDto> archiveDtos = moduleResponseDtos.getData()
                .stream()
                .map(ArchiveDto::from)
                .collect(Collectors.toList());
        return ArchivesDto.builder()
                .archives(archiveDtos)
                .build();
    }

    @Override
    public String toString() {
        return "ArchivesDto{" +
                "archives=" + archives +
                '}';
    }
}
