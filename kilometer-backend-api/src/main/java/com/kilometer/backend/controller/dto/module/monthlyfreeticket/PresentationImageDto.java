package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import com.kilometer.domain.util.FrontUrlUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PresentationImageDto {

    private static final String DEFAULT_OPACITY = "0";

    private final String url;
    private final String link;
    private final String backGroundText;
    private final String dimColor;
    private final String opacity;
    private final boolean dimTarget;

    static PresentationImageDto from(MonthlyFreeTicketDto monthlyFreeTicketDto) {
        return PresentationImageDto.builder()
                .url(monthlyFreeTicketDto.getThumbnailImageUrl())
                .link(FrontUrlUtils.getFrontDetailUrlPattern(monthlyFreeTicketDto.getItemId()))
                .backGroundText(null)
                .dimColor(null)
                .opacity(DEFAULT_OPACITY)
                .dimTarget(false)
                .build();
    }

    @Override
    public String toString() {
        return "PresentationImageDto{" +
                "url='" + url + '\'' +
                ", link='" + link + '\'' +
                ", backGroundText='" + backGroundText + '\'' +
                ", dimColor='" + dimColor + '\'' +
                ", opacity='" + opacity + '\'' +
                ", dimTarget=" + dimTarget +
                '}';
    }
}
