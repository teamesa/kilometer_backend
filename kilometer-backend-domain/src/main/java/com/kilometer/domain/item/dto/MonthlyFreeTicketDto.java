package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MonthlyFreeTicketDto {

    private Long itemId;
    private String thumbnailImageUrl;
    private String title;
    private ExhibitionType exhibitionType;
    private FeeType feeType;
    private int pickCount;
    private boolean isHearted;
    private Long archiveCount;
    private double grade;

    @Override
    public String toString() {
        return "MonthlyFreeTicketDto{" +
                "itemId=" + itemId +
                ", thumbnailImageUrl='" + thumbnailImageUrl + '\'' +
                ", title='" + title + '\'' +
                ", exhibitionType=" + exhibitionType +
                ", feeType=" + feeType +
                ", pickCount=" + pickCount +
                ", isHearted=" + isHearted +
                ", archiveCount=" + archiveCount +
                ", grade=" + grade +
                '}';
    }
}
