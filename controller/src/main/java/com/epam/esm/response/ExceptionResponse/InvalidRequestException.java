package com.epam.esm.response.ExceptionResponse;

/**
 * This class represents an exception that is thrown when an invalid request is made.
 */
public class InvalidRequestException extends Exception {
    /**
     * The object representing the violations of the invalid request.
     */
    private final Object violations;

    /**
     * Constructs an InvalidRequestException object.
     *
     * @param violations The object representing the violations of the invalid request.
     */
    public InvalidRequestException(Object violations) {
        this.violations = violations;
    }

    /**
     * Gets the object representing the violations of the invalid request.
     *
     * @return The object representing the violations of the invalid request.
     */
    public Object getViolations() {
        return violations;
    }
}
