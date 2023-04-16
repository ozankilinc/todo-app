package com.todo.app.userservice.service.business;

import com.todo.app.userservice.entity.User;
import com.todo.app.userservice.helper.JWTHelper;
import com.todo.app.userservice.mapper.UserMapper;
import com.todo.app.userservice.model.request.UserLoginRequest;
import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.model.response.LoginResponse;
import com.todo.app.userservice.model.response.UserResponse;
import com.todo.app.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserBusinessServiceImplTest {
    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JWTHelper jwtHelper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserBusinessServiceImpl userBusinessService;

    private static final String USERNAME = "john.doe@example.com";

    @Test
    void shouldCreateUser() {
        // Given
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username(USERNAME)
                .build();
        User user = User.builder()
                .email(USERNAME)
                .build();
        UserResponse userResponse = new UserResponse();
        when(userService.createUser(request)).thenReturn(user);
        when(userMapper.entityToResponse(user)).thenReturn(userResponse);

        // When
        UserResponse actualResponse = userBusinessService.createUser(request);

        // Then
        assertEquals(userResponse, actualResponse);
        verify(userMapper).entityToResponse(user);
        verify(userService).createUser(request);
    }

    @Test
    void shouldLogin() {
        // Given
        UserLoginRequest request = UserLoginRequest.builder()
                .username(USERNAME)
                .password("password")
                .build();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtHelper.generateJWTByAuthentication(authentication)).thenReturn("TOKEN");

        // When
        LoginResponse loginResponse = userBusinessService.login(request);

        // Then
        assertEquals("TOKEN", loginResponse.getAccessToken());

    }

    @Test
    void shouldGetUserByUserId() {
        // Given
        User user = User.builder()
                .email(USERNAME)
                .build();
        UserResponse response = new UserResponse();
        when(userService.getUserByUserId("USERID")).thenReturn(user);
        when(userMapper.entityToResponse(user)).thenReturn(response);

        // When
        UserResponse actualResponse = userBusinessService.getUserByUserId("USERID");

        // Then
        assertEquals(response, actualResponse);
        verify(userMapper).entityToResponse(user);
        verify(userService).getUserByUserId("USERID");
    }

}