package com.todo.app.userservice.filter;

import com.todo.app.userservice.entity.User;
import com.todo.app.userservice.enums.UserStatus;
import com.todo.app.userservice.helper.JWTHelper;
import com.todo.app.userservice.model.exception.TodoAppApiException;
import com.todo.app.userservice.model.security.TodoUserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JWTHelper jwtHelper;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private AuthenticationFilter authenticationFilter;


    private MockFilterChain mockFilterChain;
    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;

    private final String BEARER_TOKEN = "Token";
    private final String USERNAME = "john.doe@example.com";
    private TodoUserDetails todoUserDetails;
    private User user;

    @Captor
    private ArgumentCaptor<UsernamePasswordAuthenticationToken> usernamePasswordAuthenticationTokenArgumentCaptor;

    @BeforeEach
    void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer " + BEARER_TOKEN);
        mockHttpServletResponse = new MockHttpServletResponse();
        mockFilterChain = new MockFilterChain();
        SecurityContextHolder.setContext(securityContext);
        user = User.builder()
                .email(USERNAME)
                .userId("test")
                .status(UserStatus.ACTIVE.name())
                .build();
        todoUserDetails = new TodoUserDetails(user);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldDoFilterInternalWhenBearerTokenExist() throws ServletException, IOException {
        // Given
        when(jwtHelper.verifyJWTByAccessToken(BEARER_TOKEN)).thenReturn(USERNAME);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(todoUserDetails);

        // When
        authenticationFilter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

        // Then
        verify(securityContext).setAuthentication(usernamePasswordAuthenticationTokenArgumentCaptor.capture());
        UsernamePasswordAuthenticationToken value = usernamePasswordAuthenticationTokenArgumentCaptor.getValue();
        TodoUserDetails actualDetails = (TodoUserDetails) value.getPrincipal();
        assertEquals(todoUserDetails, actualDetails);
        assertEquals(user.getUserId(), actualDetails.getUserId());
    }

    @Test
    void shouldDoFilterInternalWhenBearerTokenExistButInvalid() throws ServletException, IOException {
        // Given
        doThrow(TodoAppApiException.class).when(jwtHelper).verifyJWTByAccessToken(BEARER_TOKEN);

        // When
        authenticationFilter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

        // Then
        verify(securityContext, never()).setAuthentication(usernamePasswordAuthenticationTokenArgumentCaptor.capture());
    }

    @Test
    void shouldDoFilterInternalWhenBearerTokenNotExist() throws ServletException, IOException {
        // Given
        mockHttpServletRequest.removeHeader("Authorization");

        // When
        authenticationFilter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

        // Then
        verifyNoInteractions(userDetailsService);
        verifyNoInteractions(jwtHelper);
    }
}