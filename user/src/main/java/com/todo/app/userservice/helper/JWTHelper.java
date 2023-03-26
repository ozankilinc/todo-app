package com.todo.app.userservice.helper;

import org.springframework.security.core.Authentication;

public interface JWTHelper {

    String generateJWTByAuthentication(Authentication authentication);

    String verifyJWTByAccessToken(String accessToken);
}
