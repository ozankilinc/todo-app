package com.todo.app.userservice.controller;

import com.todo.app.userservice.model.request.UserLoginRequest;
import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.model.response.LoginResponse;
import com.todo.app.userservice.model.response.UserResponse;
import com.todo.app.userservice.service.UserBusinessService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.*;

@WebMvcTest(value = AuthController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class AuthControllerTest extends BaseController {

    @MockBean
    private UserBusinessService userBusinessService;


    @Captor
    private ArgumentCaptor<UserRegisterRequest> userRegisterRequestArgumentCaptor;

    @Captor
    private ArgumentCaptor<UserLoginRequest> userLoginRequestArgumentCaptor;

    @Test
    void shouldRegisterUser() throws Exception {
        // Given
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("joh.doe@example.com")
                .password("1234")
                .name("john")
                .surname("doe")
                .build();

        UserResponse response = UserResponse.builder()
                .name("john")
                .surname("doe")
                .userId("test")
                .username("joh.doe@example.com")
                .build();

        when(userBusinessService.createUser(userRegisterRequestArgumentCaptor.capture())).thenReturn(response);

        // When
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        // Then
        verify(userBusinessService).createUser(userRegisterRequestArgumentCaptor.getValue());

    }

    @Test
    void shouldLogin() throws Exception {
        // Given
        UserLoginRequest request = UserLoginRequest.builder()
                .username("john.deo@example.com")
                .password("12345")
                .build();

        LoginResponse response = LoginResponse.builder()
                .accessToken("TOKEN")
                .build();
        when(userBusinessService.login(userLoginRequestArgumentCaptor.capture())).thenReturn(response);

        // When
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        // Then
        verify(userBusinessService).login(userLoginRequestArgumentCaptor.getValue());
    }
}