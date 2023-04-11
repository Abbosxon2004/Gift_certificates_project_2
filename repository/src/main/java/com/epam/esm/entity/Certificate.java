package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@Builder
public class Certificate {
    private long id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private Set<Tag> tags;
}
