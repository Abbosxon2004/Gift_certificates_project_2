package com.epam.esm.exception;

import com.epam.esm.util.ExceptionMessages;

/**
 * Exception thrown when a modification operation on a resource fails.
 */
public class ModificationException extends Exception {
    /**
     * Constructs a new ModificationException with the specified error message and cause.
     *
     * @param message the error message
     * @param cause   the cause of the exception
     */
    public ModificationException(ExceptionMessages message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    /**
     * Constructs a new ModificationException with the specified error message and resource ID.
     *
     * @param message the error message
     * @param id      the ID of the resource that the modification operation was performed on
     */
    public ModificationException(ExceptionMessages message, long id) {
        super(String.format(message.getMessage(), id));
    }
}