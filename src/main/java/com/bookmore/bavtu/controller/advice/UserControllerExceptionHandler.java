package com.bookmore.bavtu.controller.advice;

import com.bookmore.bavtu.exception.user.BadPasswordException;
import com.bookmore.bavtu.exception.user.IncorrectPasswordException;
import com.bookmore.bavtu.exception.user.UserExistsException;
import com.bookmore.bavtu.exception.user.UserNotFoundException;
import com.bookmore.bavtu.model.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        HttpStatus exceptionStatus = HttpStatus.NOT_FOUND;
        ExceptionDTO exceptionDTO = ExceptionDTO.convertExceptionToExceptionDTO(exceptionStatus, userNotFoundException.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionStatus);
    }

    @ExceptionHandler(value = BadPasswordException.class)
    public ResponseEntity<ExceptionDTO> handleBadPasswordException(BadPasswordException badPasswordException) {
        HttpStatus exceptionStatus = HttpStatus.BAD_REQUEST;
        ExceptionDTO exceptionDTO = ExceptionDTO.convertExceptionToExceptionDTO(exceptionStatus, badPasswordException.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionStatus);
    }

    @ExceptionHandler(value = IncorrectPasswordException.class)
    public ResponseEntity<ExceptionDTO> handleIncorrectPasswordException(IncorrectPasswordException incorrectPasswordException){
        HttpStatus exceptionStatus = HttpStatus.UNAUTHORIZED;
        ExceptionDTO exceptionDTO = ExceptionDTO.convertExceptionToExceptionDTO(exceptionStatus, incorrectPasswordException.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionStatus);
    }

    @ExceptionHandler(value = UserExistsException.class)
    public ResponseEntity<ExceptionDTO> handleUserExistsException(UserExistsException userExistsException){
        HttpStatus exceptionStatus = HttpStatus.CONFLICT;
        ExceptionDTO exceptionDTO = ExceptionDTO.convertExceptionToExceptionDTO(exceptionStatus, userExistsException.getMessage());
        return new ResponseEntity<>(exceptionDTO, exceptionStatus);
    }
}
