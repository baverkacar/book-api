package com.bookmore.bavtu.exception;

import org.springframework.http.HttpStatus;


/*
 *  This exception for User's password field.
 *  It Occurs when the password field does not provide the features requested by our @ValidPassword validation.
 */
public class BadPasswordException extends RuntimeException {
    public BadPasswordException(String msg) {
        super(msg);
    }
}
