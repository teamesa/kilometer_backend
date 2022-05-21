package com.kilometer.domain.search.additionalinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ListItemAdditionalInfo {
    private int heartCount;
    private float grade;
    private float archiveCount;
}
