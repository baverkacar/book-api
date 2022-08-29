package com.bookmore.bavtu.exception.user;

public class UserAlreadyHasGivenBookException extends RuntimeException{
    public UserAlreadyHasGivenBookException(String message) {
        super(message);
    }
}
