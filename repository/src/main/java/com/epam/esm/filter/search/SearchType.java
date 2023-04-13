package com.epam.esm.filter.search;

public enum SearchType {
    DECRIPTION("description"),

    NAME("name");


    private final String stringValue;

    SearchType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
