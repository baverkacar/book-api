package com.bookmore.bavtu.model.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionDTO {
    private final HttpStatus httpStatus;
    private final int statusCode;
    private final String message;
    private final LocalDateTime timeStamp;

    public ExceptionDTO(HttpStatus httpStatus, int statusCode, String message, LocalDateTime timeStamp) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
