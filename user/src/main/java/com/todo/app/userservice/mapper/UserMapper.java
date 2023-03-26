package com.todo.app.userservice.mapper;

import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.model.response.UserResponse;
import com.todo.app.userservice.entity.User;

public interface UserMapper {

    User requestToEntity(UserRegisterRequest request);

    UserResponse entityToResponse(User user);
}
