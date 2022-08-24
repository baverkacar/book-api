package com.bookmore.bavtu.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookmore.bavtu.exception.IncorrectPasswordException;
import com.bookmore.bavtu.exception.UserExistsException;
import com.bookmore.bavtu.exception.UserNotFoundException;
import com.bookmore.bavtu.model.api.user.DeleteUserRequest;
import com.bookmore.bavtu.model.api.user.UpdateUserPasswordRequest;
import com.bookmore.bavtu.model.api.user.UserSignUpRequest;
import com.bookmore.bavtu.model.dto.UserDTO;
import com.bookmore.bavtu.repository.UserRepository;
import com.bookmore.bavtu.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Slf4j
@AutoConfigureWebClient
public class UserControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Autowired
    private  MockMvc mockMvc;

    @Autowired
    private  ObjectMapper objectMapper;

    private final String URL = "/api/user";
    private final String CONTENT_TYPE = "application/json";

    @Test
    void whenCreateUser_WithValidInput_thenReturn201() throws Exception {
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
    void whenCreateUser_WithInvalidPassword_thenReturn400() throws Exception{
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


    @Test
    void whenCreateUser_WithExistedUser_thenReturn409() throws Exception{
        // given
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
                .username("testUser")
                .email("test-user@gmail.com")
                .password("Testuser123-")
                .build();

        // when
        when(userService.createUser(userSignUpRequest)).thenThrow(new UserExistsException("User is already created with given info."));
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
                status().isConflict()
        );
        // actions.andDo(print());
    }


    @Test
    void whenGetUser_WithValidInput_thenReturn200() throws Exception {
        // given
        String userID = "62f6a94295e39057cdb3fa27";

        UserDTO userDTO = UserDTO.builder()
                .username("testUser")
                .email("test-user@gmail.com")
                .password("Testuser123-")
                .build();

        // when
        when(userService.getUserByID(userID)).thenReturn(userDTO);
        ResultActions actions = mockMvc.perform(get(URL + "/" +userID)
                .contentType(CONTENT_TYPE));

        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(userService, Mockito.times(1)).getUserByID(captor.capture());

        actions.andExpectAll(
                status().isOk(),
                jsonPath("$.username").value("testUser"),
                jsonPath("$.email").value("test-user@gmail.com"),
                jsonPath("$.password").value("Testuser123-")
        );
        // actions.andDo(print());
    }


    @Test
    void whenGetUser_WithInvalidInput_thenReturn404() throws Exception{
        // given
        String userID = "invalidUserID";

        // when
        when(userService.getUserByID(userID)).thenThrow(new UserNotFoundException("User did not found with given id."));
        ResultActions actions = mockMvc.perform(get(URL + "/" +userID)
                .contentType(CONTENT_TYPE));

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(userService, Mockito.times(1)).getUserByID(captor.capture());

        actions.andExpectAll(
                status().isNotFound()
        );
        // actions.andDo(print());
    }


    @Test
    void whenDeleteUser_WithValidInput_thenReturn204() throws Exception{
        // given
        DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder()
                .id("62f6a94295e39057cdb3fa27")
                .password("Testuser123-")
                .build();

        // when
        when(userService.deleteUser(deleteUserRequest)).thenReturn(deleteUserRequest.getId());
        ResultActions actions = mockMvc.perform(delete(URL + "/delete")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(deleteUserRequest))
        );

        // then
        ArgumentCaptor<DeleteUserRequest> captor = ArgumentCaptor.forClass(DeleteUserRequest.class);
        verify(userService, times(1)).deleteUser(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo("62f6a94295e39057cdb3fa27");
        assertThat(captor.getValue().getPassword()).isEqualTo("Testuser123-");

        actions.andExpect(status().isNoContent());
        // actions.andDo(print());
    }


    @Test
    void whenDeleteUser_WithInvalidID_thenReturn404() throws Exception{
        // given
        DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder()
                .id("invalidID")
                .password("Testuser123-")
                .build();
        // when
        when(userService.deleteUser(deleteUserRequest)).thenThrow(new UserNotFoundException("User did not found with given id."));
        ResultActions actions = mockMvc.perform(delete(URL + "/delete")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(deleteUserRequest))
        );

        // then
        ArgumentCaptor<DeleteUserRequest> captor = ArgumentCaptor.forClass(DeleteUserRequest.class);
        verify(userService, times(1)).deleteUser(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo("invalidID");
        assertThat(captor.getValue().getPassword()).isEqualTo("Testuser123-");

        actions.andExpect(status().isNotFound());
        // actions.andDo(print());
    }


    @Test void whenDeleteUser_WithInValidPassword_thenReturn401() throws Exception{
        // given
        DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder()
                .id("62f6a94295e39057cdb3fa27")
                .password("invalidPassword")
                .build();

        // when
        when(userService.deleteUser(deleteUserRequest)).thenThrow(new IncorrectPasswordException("Passwords do not match."));
        ResultActions actions = mockMvc.perform(delete(URL + "/delete")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(deleteUserRequest))
        );

        // then
        ArgumentCaptor<DeleteUserRequest> captor = ArgumentCaptor.forClass(DeleteUserRequest.class);
        verify(userService, times(1)).deleteUser(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo("62f6a94295e39057cdb3fa27");
        assertThat(captor.getValue().getPassword()).isEqualTo("invalidPassword");

        actions.andExpect(status().isUnauthorized());
        // actions.andDo(print());
    }


    @Test
    void whenUpdateUserPassword_WithValidInput_thenReturn200() throws Exception {
        // given
        UpdateUserPasswordRequest updateUserPasswordRequest = UpdateUserPasswordRequest.builder()
                .id("62f6a94295e39057cdb3fa27")
                .currentPassword("Testuser123-")
                .newPassword("Testuser789-")
                .build();
        UserDTO userDTO = UserDTO.builder()
                .username("testUser")
                .email("test-user@gmail.com")
                .password("Testuser789-")
                .build();

        // when
        when(userService.updateUserPassword(updateUserPasswordRequest)).thenReturn(userDTO);
        ResultActions actions = mockMvc.perform(patch(URL + "/update")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(updateUserPasswordRequest))
        );

        // then
        ArgumentCaptor<UpdateUserPasswordRequest> captor = ArgumentCaptor.forClass(UpdateUserPasswordRequest.class);
        verify(userService, times(1)).updateUserPassword(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo("62f6a94295e39057cdb3fa27");
        assertThat(captor.getValue().getCurrentPassword()).isEqualTo("Testuser123-");
        assertThat(captor.getValue().getNewPassword()).isEqualTo("Testuser789-");

        actions.andExpectAll(
                status().isOk(),
                jsonPath("$.username").value("testUser"),
                jsonPath("$.email").value("test-user@gmail.com"),
                jsonPath("$.password").value("Testuser789-")
        );
        // actions.andDo(print());
    }


    @Test
    void whenUpdateUserPasswordWithInvalidID_thenReturn404() throws Exception{
        // given
        UpdateUserPasswordRequest updateUserPasswordRequest = UpdateUserPasswordRequest.builder()
                .id("invalidID")
                .currentPassword("Testuser123-")
                .newPassword("Testuser789-")
                .build();

        // when
        when(userService.updateUserPassword(updateUserPasswordRequest)).thenThrow(new UserNotFoundException("User did not found with given id."));
        ResultActions actions = mockMvc.perform(patch(URL + "/update")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(updateUserPasswordRequest))
        );

        // then
        ArgumentCaptor<UpdateUserPasswordRequest> captor = ArgumentCaptor.forClass(UpdateUserPasswordRequest.class);
        verify(userService, times(1)).updateUserPassword(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo("invalidID");
        assertThat(captor.getValue().getCurrentPassword()).isEqualTo("Testuser123-");
        assertThat(captor.getValue().getNewPassword()).isEqualTo("Testuser789-");

        actions.andExpect(status().isNotFound());
        // actions.andDo(print());
    }

    @Test
    void whenUpdateUserPassword_WithInvalidCurrentPassword_thenReturn401() throws Exception{
        // given
        UpdateUserPasswordRequest updateUserPasswordRequest = UpdateUserPasswordRequest.builder()
                .id("62f6a94295e39057cdb3fa27")
                .currentPassword("invalidPassword")
                .newPassword("Testuser789-")
                .build();

        // when
        when(userService.updateUserPassword(updateUserPasswordRequest)).thenThrow(new IncorrectPasswordException("Passwords do not match."));
        ResultActions actions = mockMvc.perform(patch(URL + "/update")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(updateUserPasswordRequest))
        );

        // then
        ArgumentCaptor<UpdateUserPasswordRequest> captor = ArgumentCaptor.forClass(UpdateUserPasswordRequest.class);
        verify(userService, times(1)).updateUserPassword(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo("62f6a94295e39057cdb3fa27");
        assertThat(captor.getValue().getCurrentPassword()).isEqualTo("invalidPassword");
        assertThat(captor.getValue().getNewPassword()).isEqualTo("Testuser789-");

        actions.andExpect(status().isUnauthorized());
        // actions.andDo(print());
    }

    @Test
    void whenUpdateUserPassword_WithInvalidCurrentPassword_thenReturn400() throws Exception{
        // given
        UpdateUserPasswordRequest updateUserPasswordRequest = UpdateUserPasswordRequest.builder()
                .id("62f6a94295e39057cdb3fa27")
                .currentPassword("Testuser123-")
                .newPassword("invalidPassword")
                .build();

        // when
        ResultActions actions = mockMvc.perform(patch(URL + "/update")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(updateUserPasswordRequest))
        );

        // then
        actions.andExpect(status().isBadRequest());
        // actions.andDo(print());
    }
}