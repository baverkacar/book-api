package com.bookmore.bavtu.service.impl;

import com.bookmore.bavtu.domain.User;
import com.bookmore.bavtu.exception.BadPasswordException;
import com.bookmore.bavtu.exception.IncorrectPasswordException;
import com.bookmore.bavtu.exception.UserNotFoundException;
import com.bookmore.bavtu.mapper.UserMapper;
import com.bookmore.bavtu.model.api.UserCreateRequest;
import com.bookmore.bavtu.model.api.UserDeleteRequest;
import com.bookmore.bavtu.model.api.UserUpdateRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import com.bookmore.bavtu.repository.UserRepository;
import com.bookmore.bavtu.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j //logger
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Create user method
     * @param userCreateRequest
     * @return UserDTO - Http Status: 201 (CREATED)
     * @throws BadPasswordException
     */
    @Override
    public ResponseEntity<UserDTO> create(UserCreateRequest userCreateRequest) throws BadPasswordException{
        try{
            //Checking given username and email are already exist or not.
            if(isUsernameExists(userCreateRequest.getUsername()) || isEmailExists(userCreateRequest.getEmail())){
                log.error("This email or username are already in use.");
                return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
            }
            // Mapping userCreateRequest api model to User mongodb document. Then saving it to Database.
            User user = userMapper.userCreateRequestToUser(userCreateRequest);
            user = userRepository.save(user);

            // After saving user, maps User to UserDTO model to returns it.
            log.info("New user created with id: " + user.getId());
            return new ResponseEntity(userMapper.userToUserDTO(user), HttpStatus.CREATED);
        }
        catch (BadPasswordException badPasswordException){
            throw new BadPasswordException("Invalid Password");
        }
    }

    /**
     * Get single user method
     * @param id
     * @return UserDTO - Http Status: 202 (ACCEPTED)
     * @throws UserNotFoundException
     */
    @Override
    public ResponseEntity<UserDTO> get(String id) {
        // Finding user with given id.
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with given id."));
        // After getting user, maps User to UserDTO model to returns it.
        return new ResponseEntity(userMapper.userToUserDTO(user), HttpStatus.ACCEPTED);
    }

    /**
     * Delete user method
     * @param userDeleteRequest
     * @return String - Http Status: 200 (OK)
     * @throws UserNotFoundException
     * @throws BadPasswordException
     */
    @Override
    public ResponseEntity<String> delete(UserDeleteRequest userDeleteRequest) {
        // Getting user id and checks if user's existence.
        String userId = userDeleteRequest.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with given id."));

        // Checking passwords' equality.
        if(user.getPassword().equals(userDeleteRequest.getPassword())){
            log.info("User deleted with given id: " + user.getId());
            userRepository.delete(user);
            return new ResponseEntity("User deleted with given id: " + userId, HttpStatus.OK);
        }
        throw new BadPasswordException("Invalid password");
    }


    /**
     * Update user's password method
     * @param userUpdateRequest
     * @return UserDTO - Http Status: 200 (OK)
     * @throws UserNotFoundException
     * @throws IncorrectPasswordException
     */
    @Override
    public ResponseEntity<UserDTO> update(UserUpdateRequest userUpdateRequest) {
        String userId = userUpdateRequest.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with given id."));

        String userPassword = userUpdateRequest.getOldPassword();
        if(user.getPassword().equals(userPassword)){
            user.setPassword(userUpdateRequest.getNewPassword());
            userRepository.save(user);
            log.info("User's password changed successfully with given id: " + userId);
            return new ResponseEntity(userMapper.userToUserDTO(user), HttpStatus.OK);
        }
        throw new IncorrectPasswordException("Password do not matched");
    }


    /**
     * INNER METHODS
     */
    //Checking username existence, if username exists returns true
    public boolean isUsernameExists(String username){
        User user = userRepository.findByUsername(username);
        return user != null ? true : false;
    }

    //Checking email existence, if email exists returns true
    public boolean isEmailExists(String email){
        User user = userRepository.findByEmail(email);
        return user != null ? true : false;
    }
}
