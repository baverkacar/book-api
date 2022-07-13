package com.bookmore.bavtu.exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(String msg){
        super(msg);
    }
}
