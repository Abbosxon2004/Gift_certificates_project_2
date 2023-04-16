package com.epam.esm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * This class represents a Data Transfer Object (DTO) for a tag.
 * It contains information such as the tag's ID, name.
 */
@Data
@Builder
public class TagDto {
    private long id;

    @NotNull
    private String name;
}
