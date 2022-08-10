package com.bookmore.bavtu.service;

import com.bookmore.bavtu.model.api.user.UserSignUpRequest;
import com.bookmore.bavtu.model.api.user.DeleteUserRequest;
import com.bookmore.bavtu.model.api.user.UpdateUserPasswordRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<UserDTO> create(UserSignUpRequest userSignUpRequest);
    public ResponseEntity<UserDTO> get(String id);
    public ResponseEntity<String> delete(DeleteUserRequest deleteUserRequest);
    public ResponseEntity<UserDTO> update(UpdateUserPasswordRequest updateUserPasswordRequest);
}
