package com.todo.app.userservice.controller;

import com.todo.app.userservice.model.response.UserResponse;
import com.todo.app.userservice.service.UserBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserBusinessService userBusinessService;

    @PreAuthorize("#userId == authentication.principal.userId")
    @GetMapping
    public ResponseEntity<UserResponse> getUserResponse(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(userBusinessService.getUserByUserId(userId));
    }
}
