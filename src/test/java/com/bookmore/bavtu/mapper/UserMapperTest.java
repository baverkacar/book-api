package com.bookmore.bavtu.mapper;

import com.bookmore.bavtu.domain.User;
import com.bookmore.bavtu.model.api.user.UserSignUpRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testUserSignUpRequestToUser() throws Exception{
        // given
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
                .username("testUser")
                .email("test-user@gmail.com")
                .password("Testuser123-")
                .build();

        // mapping
        User user = mapper.userSignUpRequestToUser(userSignUpRequest);

        // then
        assertEquals(user.getUsername(), userSignUpRequest.getUsername());
        assertEquals(user.getEmail(), userSignUpRequest.getEmail());
        assertEquals(user.getPassword(), userSignUpRequest.getPassword());
    }

    @Test
    void TestUserToUserDTO() throws Exception{
        // given
        User user = User.builder()
                .id("userID")
                .username("testUser")
                .email("test-user@gmail.com")
                .password("Testuser123-")
                .build();

        // mapping
        UserDTO userDTO = mapper.userToUserDTO(user);

        // then
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }
}