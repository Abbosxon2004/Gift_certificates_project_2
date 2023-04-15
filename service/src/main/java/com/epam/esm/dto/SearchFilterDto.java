package com.epam.esm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchFilterDto {

    @NotNull
    private String searchValue;

    @NotNull
    @Pattern(regexp = "name|description", message = "choose one option: name or description")
    private String searchType;

    @NotNull
    @Pattern(regexp = "starts_with|contains|ends_with", message = "choose one option: begins_with, contains or ends_with")
    private String searchPlace;
}
