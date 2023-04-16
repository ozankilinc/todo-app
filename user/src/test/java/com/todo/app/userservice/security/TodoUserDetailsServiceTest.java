package com.todo.app.userservice.security;

import com.todo.app.userservice.entity.User;
import com.todo.app.userservice.model.security.TodoUserDetails;
import com.todo.app.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TodoUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TodoUserDetailsService userDetailsService;

    private final String USERNAME = "john.doe@example.com";

    @Test
    void shouldLoadUserByUsername() {
        // Given
        User user = User.builder().email(USERNAME).build();
        when(userRepository.findUserByEmail(USERNAME)).thenReturn(Optional.of(user));

        // When
        UserDetails todoUserDetails = userDetailsService.loadUserByUsername(USERNAME);

        // Then
        TodoUserDetails actualTodoUserDetails = (TodoUserDetails) todoUserDetails;
        assertEquals(user.getEmail(), actualTodoUserDetails.getUsername());

    }

    @Test
    void shouldThrowUsernameNotFoundException() {
        // Given
        when(userRepository.findUserByEmail(USERNAME)).thenReturn(Optional.empty());

        // When
        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(USERNAME));

        // Then
        assertEquals("User Not Found", usernameNotFoundException.getMessage());
    }
}