package com.todo.app.userservice.controller;

import com.todo.app.userservice.model.request.UserLoginRequest;
import com.todo.app.userservice.model.request.UserRegisterRequest;
import com.todo.app.userservice.model.response.LoginResponse;
import com.todo.app.userservice.model.response.UserResponse;
import com.todo.app.userservice.service.UserBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserBusinessService userBusinessService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userBusinessService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid UserLoginRequest request) {
        return ResponseEntity.ok(userBusinessService.login(request));
    }
}
