package com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import java.time.LocalDate;

public interface MonthlyFreeTicket {
    Long getId();

    String getListImageUrl();

    String getTitle();

    ExhibitionType getExhibitionType();

    FeeType getFeeType();

    int getPickCount();

    LocalDate getStartDate();

    LocalDate getEndDate();

    Boolean getIsHearted();

    long getArchiveCount();

    Double getGrade();



}
