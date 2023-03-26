package com.todo.app.userservice.service.business;

import com.todo.app.userservice.helper.JWTHelper;
import com.todo.app.userservice.model.request.UserLoginRequest;
import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.model.response.LoginResponse;
import com.todo.app.userservice.model.response.UserResponse;
import com.todo.app.userservice.mapper.UserMapper;
import com.todo.app.userservice.entity.User;
import com.todo.app.userservice.service.UserBusinessService;
import com.todo.app.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserBusinessServiceImpl implements UserBusinessService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JWTHelper jwtHelper;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse createUser(UserRegisterRequest request) {
        User user = userService.createUser(request);
        return userMapper.entityToResponse(user);
    }

    @Override
    public LoginResponse login(UserLoginRequest request) {
        UsernamePasswordAuthenticationToken token = this.buildByUserLoginRequest(request);
        Authentication authentication = authenticationManager.authenticate(token);
        final String accessToken = jwtHelper.generateJWTByAuthentication(authentication);
        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    public UserResponse getUserByUserId(String userId) {
        User user = userService.getUserByUserId(userId);
        return userMapper.entityToResponse(user);
    }

    private UsernamePasswordAuthenticationToken buildByUserLoginRequest(UserLoginRequest request) {
        return new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    }
}
