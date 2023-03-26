package com.todo.app.userservice.mapper.impl;

import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.model.response.UserResponse;
import com.todo.app.userservice.enums.UserStatus;
import com.todo.app.userservice.generator.UserIdGenerator;
import com.todo.app.userservice.mapper.UserMapper;
import com.todo.app.userservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final UserIdGenerator userIdGenerator;

    @Override
    public User requestToEntity(UserRegisterRequest request) {
        return User.builder()
                .email(request.getUsername())
                .name(request.getName())
                .surname(request.getSurname())
                .status(UserStatus.ACTIVE.name())
                .userId(userIdGenerator.generateUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    @Override
    public UserResponse entityToResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .username(user.getEmail())
                .surname(user.getSurname())
                .userId(user.getUserId())
                .build();
    }
}
