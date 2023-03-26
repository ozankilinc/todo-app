package com.todo.app.userservice.service;

import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.entity.User;

public interface UserService {

    User createUser(UserRegisterRequest request);

    User getUserByUsername(String username);

    User getUserByUserId(String userId);
}
