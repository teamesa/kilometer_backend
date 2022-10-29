package com.kilometer.domain.search;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;

import java.time.LocalDate;

public interface ItemInfoExtraction {
    Long getId();

    String getListImageUrl();

    String getTitle();

    ExhibitionType getExhibitionType();

    FeeType getFeeType();

    LocalDate getStartDate();

    LocalDate getEndDate();

    boolean isHearted();

    String getApiType();

    String getPresentationImage();
}