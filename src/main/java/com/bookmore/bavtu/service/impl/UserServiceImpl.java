package com.bookmore.bavtu.service.impl;

import com.bookmore.bavtu.domain.User;
import com.bookmore.bavtu.exception.BadPasswordException;
import com.bookmore.bavtu.exception.IncorrectPasswordException;
import com.bookmore.bavtu.exception.UserExistsException;
import com.bookmore.bavtu.exception.UserNotFoundException;
import com.bookmore.bavtu.mapper.UserMapper;
import com.bookmore.bavtu.model.api.user.UserSignUpRequest;
import com.bookmore.bavtu.model.api.user.DeleteUserRequest;
import com.bookmore.bavtu.model.api.user.UpdateUserPasswordRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import com.bookmore.bavtu.repository.UserRepository;
import com.bookmore.bavtu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Create user method
     * @param userSignUpRequest
     * @return UserDTO
     * @throws BadPasswordException
     * @throws UserExistsException
     */
    @Override
    public UserDTO createUser(UserSignUpRequest userSignUpRequest) throws BadPasswordException, UserExistsException{
        //Checking given username and email are already exist or not.
        if(isUsernameExists(userSignUpRequest.getUsername()) || isEmailExists(userSignUpRequest.getEmail())){
            throw new UserExistsException("User is already created with given info.");
        }
        // Mapping userCreateRequest api model to User mongodb document. Then saving it to Database.
        User user = userMapper.userSignUpRequestToUser(userSignUpRequest);
        user = userRepository.save(user);

        return userMapper.userToUserDTO(user);
    }

    /**
     * Get single user method
     * @param id
     * @return UserDTO
     * @throws UserNotFoundException
     */
    @Override
    public UserDTO getUserByID(String id) throws UserNotFoundException {
        // Finding user with given id.
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User did not found with given id."));
        // After getting user, maps User to UserDTO model to returns it.
        return userMapper.userToUserDTO(user);
    }

    /**
     * Delete user method
     * @param deleteUserRequest
     * @return void
     * @throws UserNotFoundException
     * @throws IncorrectPasswordException
     */
    @Override
    public String deleteUser(DeleteUserRequest deleteUserRequest) throws UserNotFoundException, IncorrectPasswordException {
        // Getting user id and checks if user's existence.
        String userId = deleteUserRequest.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User did not found with given id."));

        // Checking passwords' equality.
        if(user.getPassword().equals(deleteUserRequest.getPassword())){
            userRepository.delete(user);
            return userId;
        }else{
            throw new IncorrectPasswordException("Passwords do not match");
        }
    }

    /**
     * Update user's password method
     * @param updateUserPasswordRequest
     * @return UserDTO
     * @throws UserNotFoundException
     * @throws IncorrectPasswordException
     */
    @Override
    public UserDTO updateUserPassword(UpdateUserPasswordRequest updateUserPasswordRequest) {
        String userId = updateUserPasswordRequest.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with given id."));

        String userPassword = updateUserPasswordRequest.getCurrentPassword();
        if(user.getPassword().equals(userPassword)){
            user.setPassword(updateUserPasswordRequest.getNewPassword());
            userRepository.save(user);
            return userMapper.userToUserDTO(user);
        }
        throw new IncorrectPasswordException("Passwords do not match");
    }

    /**
     * INNER METHODS
     */
    private boolean isUsernameExists(String username){
        User user = userRepository.findByUsername(username);
        return user != null ? true : false;
    }

    private boolean isEmailExists(String email){
        User user = userRepository.findByEmail(email);
        return user != null ? true : false;
    }
}
