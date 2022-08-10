package com.bookmore.bavtu.controller;


import com.bookmore.bavtu.exception.BadPasswordException;
import com.bookmore.bavtu.exception.IncorrectPasswordException;
import com.bookmore.bavtu.exception.UserNotFoundException;
import com.bookmore.bavtu.model.api.user.UserSignUpRequest;
import com.bookmore.bavtu.model.api.user.DeleteUserRequest;
import com.bookmore.bavtu.model.api.user.UpdateUserPasswordRequest;
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
    ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest){
        return userService.create(userSignUpRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id){
        return userService.get(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@Valid @RequestBody DeleteUserRequest deleteUserRequest){
        return userService.delete(deleteUserRequest);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UpdateUserPasswordRequest updateUserPasswordRequest){
        return userService.update(updateUserPasswordRequest);
    }
}
