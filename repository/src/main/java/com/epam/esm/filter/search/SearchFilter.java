package com.epam.esm.filter.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchFilter {
    private String searchValue;
    private Searchtype searchtype;
    private SearchPlace searchPlace;
}
