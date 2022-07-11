package com.bookmore.bavtu.service;

import com.bookmore.bavtu.model.api.UserCreateRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<UserDTO> create(UserCreateRequest userCreateRequest);


}
