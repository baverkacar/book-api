package com.bookmore.bavtu.exception;

public class BadPasswordException extends RuntimeException {
    public BadPasswordException(String msg) {
        super(msg);
    }
}
