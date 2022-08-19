package com.bookmore.bavtu.controller.api;

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
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest){
        UserDTO createdUser = userService.createUser(userSignUpRequest);
        log.debug("User created: " + createdUser);
        return new ResponseEntity(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id){
        UserDTO userDTO = userService.getUserByID(id);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@Valid @RequestBody DeleteUserRequest deleteUserRequest) {
        userService.deleteUser(deleteUserRequest);
        log.debug("User deleted with given id: " + deleteUserRequest.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UpdateUserPasswordRequest updateUserPasswordRequest){
        UserDTO updatedUser =  userService.updateUserPassword(updateUserPasswordRequest);
        log.debug("User's password changed successfully with given id: " + updatedUser);
        return new ResponseEntity(updatedUser, HttpStatus.OK);
    }
}
