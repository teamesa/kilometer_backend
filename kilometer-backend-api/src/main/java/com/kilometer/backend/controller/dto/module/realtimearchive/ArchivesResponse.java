package com.kilometer.backend.controller.dto.module.realtimearchive;

import com.kilometer.backend.util.StringConvertor;
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
public class ArchivesResponse {

    private final String topTitle;
    private final String bottomTitle;
    private final List<ArchiveDto> archives;

    public static ArchivesResponse from(ModuleResponseDto<?> moduleResponseDtos) {
        RealTimeArchiveResponse realTimeArchiveResponse = (RealTimeArchiveResponse) moduleResponseDtos.getData();
        List<ArchiveDto> archiveDtos = realTimeArchiveResponse.getArchives()
                .stream()
                .map(ArchiveDto::from)
                .collect(Collectors.toList());
        return ArchivesResponse.builder()
                .archives(archiveDtos)
                .topTitle(StringConvertor.convertNullToBlank(realTimeArchiveResponse.getTopTitle()))
                .bottomTitle(StringConvertor.convertNullToBlank(realTimeArchiveResponse.getBottomTitle()))
                .build();
    }

    @Override
    public String toString() {
        return "ArchivesResponse{" +
                "topTitle='" + topTitle + '\'' +
                ", bottomTitle='" + bottomTitle + '\'' +
                ", archives=" + archives +
                '}';
    }
}
