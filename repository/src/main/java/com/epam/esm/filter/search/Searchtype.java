package com.epam.esm.filter.search;

public enum Searchtype {
    DECRIPTION("description"),

    NAME("name");


    private final String stringValue;

    Searchtype(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
