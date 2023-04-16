package com.todo.app.userservice.helper.impl;

import com.todo.app.userservice.config.JWTProperty;
import com.todo.app.userservice.entity.User;
import com.todo.app.userservice.enums.UserStatus;
import com.todo.app.userservice.model.exception.TodoAppApiException;
import com.todo.app.userservice.model.security.TodoUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.Duration;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JWTHelperImplTest {

    @Mock
    private JWTProperty jwtProperty;

    @InjectMocks
    private JWTHelperImpl jwtHelper;

    @Test
    void shouldGenerateJWTByAuthentication() {
        // Given
        User user = User.builder()
                .userId("userId")
                .email("john.doe@example.com")
                .status(UserStatus.ACTIVE.name())
                .build();
        TodoUserDetails todoUserDetails = new TodoUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(todoUserDetails, null, todoUserDetails.getAuthorities());
        when(jwtProperty.getSecret()).thenReturn("test");
        when(jwtProperty.getDuration()).thenReturn(Duration.parse("PT3M"));
        // When
        String accessToken = jwtHelper.generateJWTByAuthentication(token);

        // Then
        assertNotNull(accessToken);
    }

    @Test
    void shouldVerifyJWTByAccessToken() {
        // Given
        User user = User.builder()
                .userId("userId")
                .email("john.doe@example.com")
                .status(UserStatus.ACTIVE.name())
                .build();
        TodoUserDetails todoUserDetails = new TodoUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(todoUserDetails, null, todoUserDetails.getAuthorities());
        when(jwtProperty.getSecret()).thenReturn("test");
        when(jwtProperty.getDuration()).thenReturn(Duration.parse("PT3M"));
        String accessToken = jwtHelper.generateJWTByAuthentication(token);

        // When
        String username = jwtHelper.verifyJWTByAccessToken(accessToken);

        // Then
        assertNotNull(username);
        assertEquals(user.getEmail(), username);
    }

    @Test
    void shouldThrowTodoApiException() {
        // Given
        User user = User.builder()
                .userId("userId")
                .email("john.doe@example.com")
                .status(UserStatus.ACTIVE.name())
                .build();
        TodoUserDetails todoUserDetails = new TodoUserDetails(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(todoUserDetails, null, todoUserDetails.getAuthorities());
        when(jwtProperty.getSecret()).thenReturn("test");
        when(jwtProperty.getDuration()).thenReturn(Duration.ZERO);
        String accessToken = jwtHelper.generateJWTByAuthentication(token);

        // When
        TodoAppApiException exception = assertThrows(TodoAppApiException.class, () -> jwtHelper.verifyJWTByAccessToken(accessToken));

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED.value(), exception.getHttpStatus());
    }

}