package com.bookmore.bavtu.mapper;


import com.bookmore.bavtu.domain.User;
import com.bookmore.bavtu.model.api.user.UserSignUpRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import org.mapstruct.Mapper;


/*
 * Mapper Class for User domain model (domain/User)
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract User userSignUpRequestToUser(UserSignUpRequest userSignUpRequest);
    public abstract UserDTO userToUserDTO(User user);
}
