package com.kilometer.domain.search.request;

import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterOptions {
    private ExhibitionType exhibitionType;
    private List<ProgressDateType> progressTypes;
    private List<FeeType> feeTypes;
    private List<RegionType> regionTypes;
}
