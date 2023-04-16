package com.epam.esm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

/**
 * This class represents a Data Transfer Object (DTO) for a certificate.
 * It contains information such as the certificate's ID, name, description, price, duration, created date, last updated date, and tags.
 */
@Data
@Builder
public class CertificateDto {
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private double price;
    @NotNull
    private int duration;
    @Pattern(regexp = "(\\d{4}-\\d{2}-\\d{2})[A-Z]+(\\d{2}:\\d{2}:\\d{2}).([0-9+-:]+)", message = "ISO 8601 format")
    private String createdDate;
    @Pattern(regexp = "(\\d{4}-\\d{2}-\\d{2})[A-Z]+(\\d{2}:\\d{2}:\\d{2}).([0-9+-:]+)", message = "ISO 8601 format")
    private String lastUpdatedDate;
    @NotNull
    private TagDto[] tags;
}
