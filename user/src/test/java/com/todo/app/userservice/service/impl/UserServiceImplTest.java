package com.todo.app.userservice.service.impl;

import com.todo.app.userservice.entity.User;
import com.todo.app.userservice.mapper.UserMapper;
import com.todo.app.userservice.model.exception.TodoAppApiException;
import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;


import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private static final String USERNAME = "john.doe@example.com";

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email(USERNAME)
                .build();
    }

    @Test
    void shouldCreateUser() {
        // Given
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username(USERNAME)
                .build();
        User user = User.builder()
                .email(USERNAME)
                .build();

        when(userRepository.existsUserByEmail(USERNAME)).thenReturn(false);
        when(userMapper.requestToEntity(request)).thenReturn(user);

        // When
        userService.createUser(request);

        // Then
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowException() {
        // Given
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username(USERNAME)
                .build();
        when(userRepository.existsUserByEmail(USERNAME)).thenReturn(true);

        // When
        TodoAppApiException todoAppApiException = assertThrows(TodoAppApiException.class, () -> userService.createUser(request));

        // Then
        assertEquals(HttpStatus.CONFLICT.value(), todoAppApiException.getHttpStatus());
    }

    @Test
    void shouldGetUserByUsername() {
        // Given
        when(userRepository.findUserByEmail(USERNAME)).thenReturn(Optional.of(user));

        // When
        User actualUser = userService.getUserByUsername(USERNAME);

        // Then
        assertEquals(user, actualUser);
        verify(userRepository).findUserByEmail(USERNAME);
        verifyNoInteractions(userMapper);
    }

    @Test
    void shouldGetUserByUserId() {
        // Given
        User user = User.builder()
                .email(USERNAME)
                .build();
        when(userRepository.findUserByUserId("USERID")).thenReturn(Optional.of(user));

        // When
        User actualUser = userService.getUserByUserId("USERID");

        // Then
        assertEquals(user, actualUser);
    }

    @Test
    void shouldThrowApiExceptionWhenUserNotFound() {
        // Given
        when(userRepository.findUserByUserId("USERID")).thenReturn(Optional.empty());

        // When
        TodoAppApiException appApiException = assertThrows(TodoAppApiException.class, () -> userService.getUserByUserId("USERID"));

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), appApiException.getHttpStatus());
    }
}