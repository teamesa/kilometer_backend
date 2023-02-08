package com.kilometer.domain.homeModules.modules.realTimeArchive.dto;

import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RealTimeArchiveResponse {

    private String topTitle;
    private String bottomTitle;
    private List<RealTimeArchiveDto> archives;

    @Override
    public String toString() {
        return "RealTimeArchiveResponse{" +
                "topTitle='" + topTitle + '\'' +
                ", bottomTitle='" + bottomTitle + '\'' +
                ", archives=" + archives +
                '}';
    }
}
