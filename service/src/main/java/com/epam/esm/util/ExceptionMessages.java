package com.epam.esm.util;

/**
 * An enum that stores exception messages used in the project
 */
public enum ExceptionMessages {
    CERTIFICATE_ID_NOT_FOUND("Requested certificate not found (id %s)"),
    CERTIFICATE_UPDATE_FAILED("Requested certificate updating failed"),
    CERTIFICATE_CREATE_FAILED("Failed to create certificate"),
    CERTIFICATE_DELETE_FAILED("Failed to delete certificate"),
    CERTIFICATES_NOT_FOUND("Failed to find certificates"),

    TAG_ID_NOT_FOUND("Requested tag not found id %s"),
    TAG_CREATE_FAILED("Failed to create tag"),
    TAG_DELETE_FAILED("Failed to delete tag"),
    TAGS_NOT_FOUND("Failed to find tags");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
