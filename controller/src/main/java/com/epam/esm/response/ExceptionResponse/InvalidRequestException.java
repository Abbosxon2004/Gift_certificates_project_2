package com.epam.esm.response.ExceptionResponse;

public class InvalidRequestException extends Exception {
    private final Object violations;

    public InvalidRequestException(Object violations) {
        this.violations = violations;
    }

    public Object getViolations() {
        return violations;
    }
}
