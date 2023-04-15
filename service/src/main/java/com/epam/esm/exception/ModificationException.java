package com.epam.esm.exception;

import com.epam.esm.util.ExceptionMessages;

public class ModificationException extends Exception {
    public ModificationException(ExceptionMessages message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public ModificationException(ExceptionMessages message, long id) {
        super(String.format(message.getMessage(), id));
    }
}