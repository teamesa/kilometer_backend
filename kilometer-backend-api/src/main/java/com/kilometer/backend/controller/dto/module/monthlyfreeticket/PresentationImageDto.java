package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import com.kilometer.domain.util.FrontUrlUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PresentationImageDto {

    public static final String OPACITY = "0";

    private String url;
    private String link;
    private String backGroundText;
    private String dimColor;
    private String opacity;
    private boolean dimTarget;

    static PresentationImageDto from(MonthlyFreeTicketDto monthlyFreeTicketDto) {
        return PresentationImageDto.builder()
                .url(monthlyFreeTicketDto.getThumbnailImageUrl())
                .link(FrontUrlUtils.getFrontDetailUrlPattern(monthlyFreeTicketDto.getItemId()))
                .opacity(OPACITY)
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
