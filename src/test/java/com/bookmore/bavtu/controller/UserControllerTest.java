package com.bookmore.bavtu.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookmore.bavtu.domain.User;
import com.bookmore.bavtu.model.api.user.UserSignUpRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import com.bookmore.bavtu.repository.UserRepository;
import com.bookmore.bavtu.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Slf4j
@AutoConfigureWebClient
public class UserControllerTest {

    @MockBean
    UserServiceImpl userService;

    @MockBean
    UserRepository userRepository;

    @Autowired
    private  MockMvc mockMvc;
    @Autowired
    private  ObjectMapper objectMapper;
    private final String URL = "/api/user";
    private final String CONTENT_TYPE = "application/json";

    @Test
    void whenCreateUserWithValidInput_thenReturn201() throws Exception {
        // given
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
                .username("testUser")
                .email("test-user@gmail.com")
                .password("Testuser123-")
                .build();
        UserDTO userDTO = UserDTO.builder()
                .username("testUser")
                .email("test-user@gmail.com")
                .password("Testuser123-")
                .build();


        // when
        when(userService.createUser(userSignUpRequest)).thenReturn(userDTO);
        ResultActions actions = mockMvc.perform(post(URL + "/create")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(userSignUpRequest))
        );

        // then
        ArgumentCaptor<UserSignUpRequest> captor = ArgumentCaptor.forClass(UserSignUpRequest.class);
        verify(userService, Mockito.times(1)).createUser(captor.capture());
        assertThat(captor.getValue().getUsername()).isEqualTo("testUser");
        assertThat(captor.getValue().getEmail()).isEqualTo("test-user@gmail.com");
        assertThat(captor.getValue().getPassword()).isEqualTo("Testuser123-");

        actions.andExpectAll(
                status().isCreated(),
                jsonPath("$.username").value("testUser"),
                jsonPath("$.email").value("test-user@gmail.com"),
                jsonPath("$.password").value("Testuser123-")
        );
        // actions.andDo(print());
    }

    @Test
    void whenCreateUserWithInvalidPassword_thenReturn400() throws Exception{
        // given
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
                .username("testUser")
                .email("test-user@gmail.com")
                .password("invalid")
                .build();

        // when
        ResultActions actions = mockMvc.perform(post(URL + "/create")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(userSignUpRequest))
        );

        // then
        actions.andExpect(status().isBadRequest());
        // actions.andDo(print());
    }

    /* @Test
    void whenGetUserWithValidInput_thenReturn200() throws Exception {
        // given
        String userID = "62f6a94295e39057cdb3fa27";

        UserDTO userDTO = UserDTO.builder()
                .username("baver")
                .email("baver@gmail.com")
                .password("Bavkcr58-")
                .build();

        // when
        when(userService.getUserByID(userID)).thenReturn(userDTO);
        ResultActions actions = mockMvc.perform(get(URL + userID)
                .contentType(CONTENT_TYPE));
        actions.andExpectAll(
                status().isOk(),
                jsonPath("$.username").value("baver"),
                jsonPath("$.email").value("baver@gmail.com"),
                jsonPath("$.password").value("Bavkcr58-")
        );
    }

    /*@Nested
    class NestedTestClassForUserExsistsException{

        @Test
        void whenTryToCreate_CreatedUser_thenReturn409() throws Exception{
            User user = User.builder()
                    .username("testUser")
                    .email("test-user@gmail.com")
                    .password("TestUser123-")
                    .build();
            userRepository.save(user);
            UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
                    .username("testUser")
                    .email("test-user@gmail.com")
                    .password("TestUser123-")
                    .build();

            ResultActions actions = mockMvc.perform(post(URL + "/create")
                    .contentType(CONTENT_TYPE)
                    .content(objectMapper.writeValueAsString(userSignUpRequest))
            );
            actions.andExpect(status().isConflict());
            actions.andDo(print());
        }
    }*/
}