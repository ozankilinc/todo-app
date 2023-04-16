package com.todo.app.userservice.mapper.impl;

import com.todo.app.userservice.entity.User;
import com.todo.app.userservice.generator.UserIdGenerator;
import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.model.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserIdGenerator userIdGenerator;

    @InjectMocks
    private UserMapperImpl userMapper;

    @Test
    void shouldMapRequestToEntity() {
        // Given
        UserRegisterRequest request = UserRegisterRequest.builder()
                .name("john")
                .surname("doe")
                .username("john.doe@example.com")
                .password("test")
                .build();

        when(userIdGenerator.generateUserId()).thenReturn("userId");
        when(passwordEncoder.encode("test")).thenReturn("encodedTest");

        // When
        User user = userMapper.requestToEntity(request);

        // Then
        assertEquals(request.getUsername(), user.getEmail());
        assertEquals(request.getName(), user.getName());
        assertEquals("encodedTest", user.getPassword());
        assertEquals("userId", user.getUserId());
        verify(passwordEncoder).encode(request.getPassword());
        verify(userIdGenerator).generateUserId();
    }

    @Test
    void shouldMapEntityToResponse() {
        // Given
        User user = User.builder()
                .userId("userId")
                .email("test@example.com")
                .name("test")
                .surname("test")
                .build();

        // When
        UserResponse response = userMapper.entityToResponse(user);

        // Then
        assertEquals(user.getEmail(), response.getUsername());
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getSurname(), response.getSurname());
        assertEquals(user.getUserId(), response.getUserId());
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(userIdGenerator);
    }
}