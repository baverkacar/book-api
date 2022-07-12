package com.bookmore.bavtu.controller;


import com.bookmore.bavtu.exception.BadPasswordException;
import com.bookmore.bavtu.exception.UserNotFoundException;
import com.bookmore.bavtu.model.api.UserCreateRequest;
import com.bookmore.bavtu.model.api.UserDeleteRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import com.bookmore.bavtu.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    /**
     *  CRUD ENDPOINTS
     */
    @PostMapping("/create")
    ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest){
        return userService.create(userCreateRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id){
        return userService.get(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@Valid @RequestBody UserDeleteRequest userDeleteRequest){
        return userService.delete(userDeleteRequest);
    }


    /**
    *   EXCEPTION HANDLERS
    */

    @ExceptionHandler(BadPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleBadPasswordException(BadPasswordException ex) {
        log.error("BadPasswordException occurred");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleUserNotFoundException(UserNotFoundException ex){
        log.error("UserNotFoundException occurred");
    }
}
