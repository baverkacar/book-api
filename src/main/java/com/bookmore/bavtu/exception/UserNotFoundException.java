package com.bookmore.bavtu.exception;


/*
* If given user is not in our database this error will occur.
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg){
        super(msg);
    }
}
