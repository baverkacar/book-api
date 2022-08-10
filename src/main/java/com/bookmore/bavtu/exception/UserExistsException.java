package com.bookmore.bavtu.exception;

/*
    This exception occurs when given email or username from user exists.
 */
public class UserExistsException extends RuntimeException{
    public UserExistsException(String message) {
        super(message);
    }
}
