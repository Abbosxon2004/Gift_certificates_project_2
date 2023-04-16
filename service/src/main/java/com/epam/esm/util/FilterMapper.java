package com.epam.esm.util;

import com.epam.esm.dto.SearchFilterDto;
import com.epam.esm.dto.SortFilterDto;
import com.epam.esm.filter.search.FilterSearch;
import com.epam.esm.filter.search.SearchPlace;
import com.epam.esm.filter.search.SearchType;
import com.epam.esm.filter.sort.FilterSort;
import com.epam.esm.filter.sort.SortOrder;
import com.epam.esm.filter.sort.SortType;
import com.epam.esm.service.CertificateService;

/**
 * This is a mapper interface that maps filter and sort DTOs to corresponding filter and sort objects.
 * It uses the Spring component model and the CertificateService class.
 * It also provides default methods to map String inputs to corresponding Enum types.
 */
@org.mapstruct.Mapper(componentModel = "spring", uses = CertificateService.class)
public interface FilterMapper {
    FilterSort toSortFilter(SortFilterDto sortFilterDTO);

    FilterSearch toSearchFilter(SearchFilterDto searchFilterDTO);

    default SortType toSortType(String sortType) {
        if (sortType == null) {
            return null;
        }
        return SortType.valueOf(sortType.toUpperCase());
    }

    default SortOrder toSortOrder(String sortOrder) {
        if (sortOrder == null) {
            return null;
        }
        return SortOrder.valueOf(sortOrder.toUpperCase());
    }

    default SearchType toSearchType(String searchType) {
        if (searchType == null) {
            return null;
        }
        return SearchType.valueOf(searchType.toUpperCase());
    }

    default SearchPlace toSearchPlace(String searchPlace) {
        if (searchPlace == null) {
            return null;
        }
        return SearchPlace.valueOf(searchPlace.toUpperCase());
    }

}
