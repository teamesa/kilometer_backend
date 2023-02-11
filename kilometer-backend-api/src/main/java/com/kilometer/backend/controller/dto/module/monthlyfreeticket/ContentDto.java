package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentDto {

    private final Long id;
    private final PresentationImageDto presentationImage;
    private final TypeBadgeDto typeBadge;
    private final List<TypeBadgeDto> additionalBadgeList;
    private final TitleDto title;
    private final HeartDto heart;
    private final ListItemAdditionalInfoDto listItemAdditionalInfo;

    static ContentDto from(MonthlyFreeTicketDto monthlyFreeTicketDto) {
        return ContentDto.builder()
                .id(monthlyFreeTicketDto.getItemId())
                .presentationImage(PresentationImageDto.from(monthlyFreeTicketDto))
                .typeBadge(TypeBadgeDto.of(monthlyFreeTicketDto.getExposureType().getDescription(), true))
                .additionalBadgeList(List.of(
                        TypeBadgeDto.of(monthlyFreeTicketDto.getExposureType().name(), false),
                        TypeBadgeDto.of(monthlyFreeTicketDto.getFeeType().getDescription(), false)
                ))
                .title(TitleDto.from(monthlyFreeTicketDto))
                .heart(HeartDto.from(monthlyFreeTicketDto))
                .listItemAdditionalInfo(ListItemAdditionalInfoDto.from(monthlyFreeTicketDto))
                .build();
    }

    @Override
    public String toString() {
        return "ContentDto{" +
                "id=" + id +
                ", presentationImage=" + presentationImage +
                ", typeBadge=" + typeBadge +
                ", additionalBadgeList=" + additionalBadgeList +
                ", title=" + title +
                ", heart=" + heart +
                ", listItemAdditionalInfo=" + listItemAdditionalInfo +
                '}';
    }
}
