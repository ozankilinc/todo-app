package com.todo.app.userservice.service;

import com.todo.app.userservice.model.request.UserLoginRequest;
import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.model.response.LoginResponse;
import com.todo.app.userservice.model.response.UserResponse;

public interface UserBusinessService {

    UserResponse createUser(UserRegisterRequest request);

    LoginResponse login(UserLoginRequest request);

    UserResponse getUserByUserId(String userId);
}
