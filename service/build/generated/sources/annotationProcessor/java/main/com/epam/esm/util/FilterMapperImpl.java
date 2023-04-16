package com.epam.esm.util;

import com.epam.esm.dto.SearchFilterDto;
import com.epam.esm.dto.SortFilterDto;
import com.epam.esm.filter.search.FilterSearch;
import com.epam.esm.filter.sort.FilterSort;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-16T12:45:19+0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class FilterMapperImpl implements FilterMapper {

    @Override
    public FilterSort toSortFilter(SortFilterDto sortFilterDTO) {
        if ( sortFilterDTO == null ) {
            return null;
        }

        FilterSort.FilterSortBuilder filterSort = FilterSort.builder();

        filterSort.sortType( toSortType( sortFilterDTO.getSortType() ) );
        filterSort.sortOrder( toSortOrder( sortFilterDTO.getSortOrder() ) );

        return filterSort.build();
    }

    @Override
    public FilterSearch toSearchFilter(SearchFilterDto searchFilterDTO) {
        if ( searchFilterDTO == null ) {
            return null;
        }

        FilterSearch.FilterSearchBuilder filterSearch = FilterSearch.builder();

        filterSearch.searchValue( searchFilterDTO.getSearchValue() );
        filterSearch.searchType( toSearchType( searchFilterDTO.getSearchType() ) );
        filterSearch.searchPlace( toSearchPlace( searchFilterDTO.getSearchPlace() ) );

        return filterSearch.build();
    }
}
