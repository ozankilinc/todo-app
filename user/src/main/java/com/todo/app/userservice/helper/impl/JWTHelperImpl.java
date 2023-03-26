package com.todo.app.userservice.helper.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.todo.app.userservice.config.JWTProperty;
import com.todo.app.userservice.helper.JWTHelper;
import com.todo.app.userservice.model.exception.TodoAppApiException;
import com.todo.app.userservice.model.security.TodoUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Calendar;
import java.util.Map;


@Component
@Slf4j
@RequiredArgsConstructor
public class JWTHelperImpl implements JWTHelper {

    private final JWTProperty jwtProperty;


    @Override
    public String generateJWTByAuthentication(Authentication authentication) {
        TodoUserDetails userDetails = (TodoUserDetails) authentication.getPrincipal();
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("user_id", userDetails.getUserId())
                .withIssuedAt(now)
                .withExpiresAt(now.plus(jwtProperty.getDuration()))
                .sign(Algorithm.HMAC256(jwtProperty.getSecret()));
    }

    @Override
    public String verifyJWTByAccessToken(String accessToken) {
        Map<String, Claim> claimMap = this.getClaimsAndValidate(accessToken);
        return claimMap.get("sub").asString();
    }

    private Map<String, Claim> getClaimsAndValidate(String accessToken) {
        try {
            final DecodedJWT decodedJWT = JWT.decode(accessToken);
            final Algorithm algorithm = Algorithm.HMAC256(jwtProperty.getSecret());
            algorithm.verify(decodedJWT);
            if (decodedJWT.getExpiresAt().before(Calendar.getInstance().getTime())) {
                throw new TodoAppApiException(HttpStatus.UNAUTHORIZED.value(), "Invalid Token");
            }
            return decodedJWT.getClaims();
        } catch (Exception e) {
            log.error("Exception Occurred While Validating Token", e);
            throw new TodoAppApiException(HttpStatus.UNAUTHORIZED.value(), "Invalid Token");

        }
    }
}
