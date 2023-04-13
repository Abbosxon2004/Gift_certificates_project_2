package com.epam.esm.filter.sort;

public enum SortOrder {

    ASC("asc"),
    DESC("desc");
    private final String stringValue;

    SortOrder(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
