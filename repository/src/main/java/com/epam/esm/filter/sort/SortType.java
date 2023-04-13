package com.epam.esm.filter.sort;

public enum SortType {

    NAME("name"),
    CREATED_DATE("created_date"),
    LAST_UPDATED_DATE("last_updated_date");
    private final String stringValue;

    SortType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
