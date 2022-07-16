package com.bookmore.bavtu.service;

import com.bookmore.bavtu.model.api.user.UserCreateRequest;
import com.bookmore.bavtu.model.api.user.UserDeleteRequest;
import com.bookmore.bavtu.model.api.user.UserUpdateRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<UserDTO> create(UserCreateRequest userCreateRequest);
    public ResponseEntity<UserDTO> get(String id);
    public ResponseEntity<String> delete(UserDeleteRequest userDeleteRequest);
    public ResponseEntity<UserDTO> update(UserUpdateRequest userUpdateRequest);
}
