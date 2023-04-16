package com.epam.esm.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * This class represents a response object that can contain a message, status, and content.
 *
 * @param <T> The type of the content to be included in the response.
 */
@Data
public class Response<T> {
    private HttpStatus status;
    private String message;
    private T content;

    public Response(T content, HttpStatus status, String message) {
        this.content = content;
        this.status = status;
        this.message = message;
    }

    public Response(HttpStatus status, String message) {
        this.content = null;
        this.status = status;
        this.message = message;
    }

    public Response(HttpStatus status, T content) {
        this.content = content;
        this.status = status;
        this.message = null;
    }

    public Response(T content) {
        this.content = content;
    }
}
