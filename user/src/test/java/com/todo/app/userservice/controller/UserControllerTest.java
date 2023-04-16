package com.todo.app.userservice.controller;

import com.todo.app.userservice.model.response.UserResponse;
import com.todo.app.userservice.service.UserBusinessService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.*;

@WebMvcTest(value = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserControllerTest extends BaseController {

    @MockBean
    private UserBusinessService userBusinessService;

    @Test
    void shouldGetUserResponse() throws Exception {
        // Given
        UserResponse response = UserResponse.builder()
                .name("john")
                .surname("doe")
                .userId("test")
                .username("joh.doe@example.com")
                .build();

        when(userBusinessService.getUserByUserId("test")).thenReturn(response);

        // When
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-User-Id", "test"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        // Then
        verify(userBusinessService).getUserByUserId("test");
    }

}