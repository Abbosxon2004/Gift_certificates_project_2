package com.epam.esm.filter.sort;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterSort {
    private SortType sortType;
    private SortOrder sortOrder;
}
