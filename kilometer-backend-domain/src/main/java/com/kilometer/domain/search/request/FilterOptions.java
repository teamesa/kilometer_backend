package com.kilometer.domain.search.request;

import com.kilometer.domain.item.ExhibitionType;
import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FilterOptions {
    private ExhibitionType exhibitionType;
    private ProgressDateType progressType;
    private FeeType feeType;
    private RegionType regionType;
}
