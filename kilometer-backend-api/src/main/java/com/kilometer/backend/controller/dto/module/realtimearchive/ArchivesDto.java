package com.kilometer.backend.controller.dto.module.realtimearchive;

import com.kilometer.backend.util.Convertor;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import com.kilometer.domain.homeModules.modules.realTimeArchive.dto.RealTimeArchiveResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ArchivesDto {

    private String toptitle;
    private String bottomTittle;
    private List<ArchiveDto> archives;

    public static ArchivesDto from(ModuleResponseDto<RealTimeArchiveResponse> moduleResponseDtos) {
        RealTimeArchiveResponse realTimeArchiveResponse = moduleResponseDtos.getData();
        List<ArchiveDto> archiveDtos = realTimeArchiveResponse.getArchives()
                .stream()
                .map(ArchiveDto::from)
                .collect(Collectors.toList());
        return ArchivesDto.builder()
                .archives(archiveDtos)
                .toptitle(Convertor.convertNullToBlank(realTimeArchiveResponse.getTopTitle()))
                .bottomTittle(Convertor.convertNullToBlank(realTimeArchiveResponse.getBottomTitle()))
                .build();
    }

    @Override
    public String toString() {
        return "ArchivesDto{" +
                "archives=" + archives +
                '}';
    }
}
