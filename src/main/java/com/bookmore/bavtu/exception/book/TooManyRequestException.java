package com.bookmore.bavtu.exception.book;

import com.bookmore.bavtu.model.api.book.GoogleBook;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TooManyRequestException extends RuntimeException{
    public TooManyRequestException(String s) {
        super(s);
    }
}
