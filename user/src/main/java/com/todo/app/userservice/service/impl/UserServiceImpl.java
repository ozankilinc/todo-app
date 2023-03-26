package com.todo.app.userservice.service.impl;

import com.todo.app.userservice.model.exception.TodoAppApiException;
import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.mapper.UserMapper;
import com.todo.app.userservice.entity.User;
import com.todo.app.userservice.repository.UserRepository;
import com.todo.app.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User createUser(UserRegisterRequest request) {
        if (userRepository.existsUserByEmail(request.getUsername())) {
            throw new TodoAppApiException(HttpStatus.CONFLICT.value(), "User Already Exist");
        }
        return userRepository.save(userMapper.requestToEntity(request));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new TodoAppApiException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
    }

    @Override
    public User getUserByUserId(String userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(() -> new TodoAppApiException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
    }
}
