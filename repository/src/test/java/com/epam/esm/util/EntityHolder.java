package com.epam.esm.util;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.filter.search.FilterSearch;
import com.epam.esm.filter.search.SearchPlace;
import com.epam.esm.filter.search.SearchType;
import com.epam.esm.filter.sort.FilterSort;
import com.epam.esm.filter.sort.SortOrder;
import com.epam.esm.filter.sort.SortType;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Set;

@UtilityClass
public class EntityHolder {
    public static final Tag tag;
    public static final Certificate certificate;
    public static final FilterSort sortFilter;
    public static final FilterSearch searchFilter;
    private static final LocalDateTime localDateTime = LocalDateTime.of(
            2000, 1, 1, 1, 00, 00);

    static {
        tag = Tag.builder()
                .id(1l)
                .name("tag1")
                .build();

        certificate = Certificate.builder()
                .id(1l)
                .name("certificate1")
                .price(0.0)
                .description("desciption1")
                .duration(0)
                .createdDate(localDateTime)
                .lastUpdatedDate(localDateTime)
                .tags(Set.of(tag)).build();

        sortFilter = FilterSort
                .builder()
                .sortType(SortType.NAME)
                .sortOrder(SortOrder.ASC)
                .build();

        searchFilter = FilterSearch
                .builder()
                .searchValue("certificate1")
                .searchType(SearchType.NAME)
                .searchPlace(SearchPlace.STARTS_WITH)
                .build();
    }
}
