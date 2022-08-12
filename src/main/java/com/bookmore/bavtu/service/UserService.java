package com.bookmore.bavtu.service;

import com.bookmore.bavtu.model.api.user.UserSignUpRequest;
import com.bookmore.bavtu.model.api.user.DeleteUserRequest;
import com.bookmore.bavtu.model.api.user.UpdateUserPasswordRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public UserDTO createUser(UserSignUpRequest userSignUpRequest);
    public UserDTO getUserByID(String id);
    public void deleteUser(DeleteUserRequest deleteUserRequest);
    public UserDTO updateUserPassword(UpdateUserPasswordRequest updateUserPasswordRequest);
}
