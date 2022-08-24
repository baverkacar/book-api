package com.bookmore.bavtu.controller.advice;

import com.bookmore.bavtu.exception.book.BookNotFoundException;
import com.bookmore.bavtu.exception.user.UserNotFoundException;
import com.bookmore.bavtu.model.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GoogleBooksAPIExceptionHandler {

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleUserNotFoundException(BookNotFoundException bookNotFoundException) {
        HttpStatus exceptionStatus = HttpStatus.NOT_FOUND;
        ExceptionDTO exceptionDTO = ExceptionDTO.convertExceptionToExceptionDTO(exceptionStatus, bookNotFoundException.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionStatus);
    }
}
