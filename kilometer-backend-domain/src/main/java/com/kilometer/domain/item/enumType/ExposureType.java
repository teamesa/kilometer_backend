package com.kilometer.domain.item.enumType;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExposureType {

    ON("전시"), OFF("미전시");

    private final String description;

    public static ExposureType findExposureType(final LocalDate startDate, final LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (isAfterOrEqual(now, startDate) && isBeforeOrEqual(now, endDate)) {
            return ON;
        }
        return OFF;
    }

    private static boolean isAfterOrEqual(final LocalDate referenceTime, final LocalDate localDate) {
        return referenceTime.isEqual(localDate) || referenceTime.isAfter(localDate);
    }

   private static boolean isBeforeOrEqual(final LocalDate referenceTime, final LocalDate localDate) {
        return referenceTime.isEqual(localDate) || referenceTime.isBefore(localDate);
   }
}
