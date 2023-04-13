package com.epam.esm.filter.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterSearch {
    private String searchValue;
    private SearchType searchType;
    private SearchPlace searchPlace;
}
