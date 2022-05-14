package com.kilometer.domain.search.request;

import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterOptions {
    private ProgressDateType progressType;
    private FeeType feeType;
    private RegionType regionType;
}
