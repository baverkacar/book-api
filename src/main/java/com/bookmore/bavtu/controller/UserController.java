package com.bookmore.bavtu.controller;


import com.bookmore.bavtu.exception.BadPasswordException;
import com.bookmore.bavtu.model.api.UserCreateRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import com.bookmore.bavtu.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@Controller
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/create")
    ResponseEntity<UserDTO> create(@Valid @RequestBody UserCreateRequest userCreateRequest){
        return userService.create(userCreateRequest);
    }

    @ExceptionHandler(BadPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadPasswordException(BadPasswordException ex) {
        return "{\n" +
                "\tError code: 400\n" +
                "\tMessage: Invalid Password\n"+
                "}";
    }
}
