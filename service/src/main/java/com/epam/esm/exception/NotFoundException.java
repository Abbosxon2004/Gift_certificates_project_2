package com.epam.esm.exception;

import com.epam.esm.util.ExceptionMessages;

/**
 * This exception is thrown when the requested resource is not found.
 * It extends the Exception class.
 */
public class NotFoundException extends Exception {

    /**
     * Constructs a new NotFoundException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public NotFoundException(ExceptionMessages message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message.
     * @param id      the id of the resource that was not found.
     */
    public NotFoundException(ExceptionMessages message, long id) {
        super(String.format(message.getMessage(), id));
    }
}