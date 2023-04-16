package com.todo.app.userservice.generator.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserIdGeneratorImplTest {

    @InjectMocks
    private UserIdGeneratorImpl userIdGenerator;

    @Test
    void shouldGenerateUserId() {
        // When
        String userId = userIdGenerator.generateUserId();

        // Then
        assertNotNull(userId);
    }

}