package com.epam.esm.filter.search;

public enum SearchPlace {
    STARTS_WITH("' %'"),
    CONTAINS("'% %'"),
    ENDS_WITH("'% '");

    private final String stringValue;

    SearchPlace(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue(String searchValue) {
        return stringValue.replace(" ", searchValue);
    }
}
