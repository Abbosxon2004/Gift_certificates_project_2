package com.epam.esm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SortFilterDto {
    @NotNull
    @Pattern(regexp = "name|created_date|last_updated_date", message = "choose one option: name, create_date or last_update_date")
    private String sortType;

    @NotNull
    @Pattern(regexp = "asc|desc", message = "choose one option: ascending or descending order")
    private String sortOrder;
}
