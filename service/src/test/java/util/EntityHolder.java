package util;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.SearchFilterDto;
import com.epam.esm.dto.SortFilterDto;
import com.epam.esm.dto.TagDto;
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
    private static final LocalDateTime localDateTime = LocalDateTime.of(
            2000, 1, 1, 1, 00, 00);
    public static final Tag tag = Tag.builder().
            id(1l)
            .name("tag")
            .build();
    public static final Certificate certificate = Certificate.builder()
            .id(1L)
            .name("certificate")
            .description("description")
            .price(0.0)
            .duration(0)
            .createdDate(localDateTime)
            .lastUpdatedDate(localDateTime)
            .tags(Set.of(tag))
            .build();
    public static final FilterSort sortFilter = FilterSort.builder()
            .sortType(SortType.NAME)
            .sortOrder(SortOrder.ASC)
            .build();
    public static final FilterSearch searchFilter = FilterSearch.builder()
            .searchValue("certificate")
            .searchType(SearchType.NAME)
            .searchPlace(SearchPlace.STARTS_WITH)
            .build();

    public static final TagDto tagDTO = TagDto.builder()
            .id(0L)
            .name("tag")
            .build();
    public static final CertificateDto certificateDTO = CertificateDto.builder()
            .id(0L)
            .name("name")
            .description("description")
            .price(0.0)
            .duration(0)
            .createdDate(localDateTime.toString())
            .lastUpdatedDate(localDateTime.toString())
            .tags(new TagDto[]{tagDTO})
            .build();
    public static final SortFilterDto sortFilterDTO = SortFilterDto.builder()
            .sortType(SortType.NAME.toString())
            .sortOrder(SortOrder.ASC.toString())
            .build();
    public static final SearchFilterDto searchFilterDTO = SearchFilterDto.builder()
            .searchValue("name")
            .searchType(SearchType.NAME.toString())
            .searchPlace(SearchPlace.STARTS_WITH.toString())
            .build();
}
