package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ListItemAdditionalInfoDto {

    private final int heartCount;
    private final double grade;
    private final Long archiveCount;

    static ListItemAdditionalInfoDto from(MonthlyFreeTicketDto monthlyFreeTicketDto) {
        return ListItemAdditionalInfoDto.builder()
                .heartCount(monthlyFreeTicketDto.getPickCount())
                .grade(monthlyFreeTicketDto.getGrade())
                .archiveCount(monthlyFreeTicketDto.getArchiveCount())
                .build();
    }

   @Override
   public String toString() {
      return "ListItemAdditionalInfo{" +
              "heartCount=" + heartCount +
              ", grade=" + grade +
              ", archiveCount=" + archiveCount +
              '}';
   }
}
