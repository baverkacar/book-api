package com.bookmore.bavtu.service.impl;

import com.bookmore.bavtu.domain.User;
import com.bookmore.bavtu.exception.BadPasswordException;
import com.bookmore.bavtu.mapper.UserMapper;
import com.bookmore.bavtu.model.api.UserCreateRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import com.bookmore.bavtu.repository.UserRepository;
import com.bookmore.bavtu.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public ResponseEntity<UserDTO> create(UserCreateRequest userCreateRequest) throws BadPasswordException{
        User user = userMapper.userCreateRequestToUser(userCreateRequest);
        user = userRepository.save(user);
        return new ResponseEntity(userMapper.userToUserDTO(user), HttpStatus.OK);
    }

}
