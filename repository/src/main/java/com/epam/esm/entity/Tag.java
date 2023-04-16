package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;

/**
 * This is a Java class that defines a Tag entity.
 */
@Data
@Builder
public class Tag implements Comparable<Tag> {
    private long id;
    private String name;

    @Override
    public int compareTo(Tag o) {
        return this.getName().compareTo(o.getName());
    }
}
