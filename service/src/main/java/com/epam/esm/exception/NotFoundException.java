package com.epam.esm.exception;

import com.epam.esm.util.ExceptionMessages;

public class NotFoundException extends Exception {
    public NotFoundException(ExceptionMessages message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public NotFoundException(ExceptionMessages message, long id) {
        super(String.format(message.getMessage(), id));
    }
}