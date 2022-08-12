package com.bookmore.bavtu.exception;

import com.bookmore.bavtu.model.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        HttpStatus exceptionStatus = HttpStatus.NOT_FOUND;
        ExceptionDTO exceptionDTO = ExceptionDTO.convertExceptionToExceptionDTO(exceptionStatus, userNotFoundException.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionStatus);
    }

    @ExceptionHandler(value = BadPasswordException.class)
    public ResponseEntity<Object> handleBadPasswordException(BadPasswordException badPasswordException) {
        HttpStatus exceptionStatus = HttpStatus.BAD_REQUEST;
        ExceptionDTO exceptionDTO = ExceptionDTO.convertExceptionToExceptionDTO(exceptionStatus, badPasswordException.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionStatus);
    }

    @ExceptionHandler(value = IncorrectPasswordException.class)
    public ResponseEntity<Object> handleIncorrectPasswordException(IncorrectPasswordException incorrectPasswordException){
        HttpStatus exceptionStatus = HttpStatus.NOT_ACCEPTABLE;
        ExceptionDTO exceptionDTO = ExceptionDTO.convertExceptionToExceptionDTO(exceptionStatus, incorrectPasswordException.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionStatus);
    }

    @ExceptionHandler(value = UserExistsException.class)
    public ResponseEntity<Object> handleUserExistsException(UserExistsException userExistsException){
        HttpStatus exceptionStatus = HttpStatus.CONFLICT;
        ExceptionDTO exceptionDTO = ExceptionDTO.convertExceptionToExceptionDTO(exceptionStatus, userExistsException.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionStatus);
    }

    // Inner method to creating exception payload.

}
