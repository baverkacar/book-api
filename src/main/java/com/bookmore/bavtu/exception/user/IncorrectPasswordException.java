package com.bookmore.bavtu.exception.user;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(String msg){
        super(msg);
    }
}
