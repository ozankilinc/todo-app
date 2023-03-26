package com.todo.app.gatewayserver.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<FilterConfig>{
    private static final String BEARER_PREFIX = "Bearer ";

    public AuthFilter() {
        super(FilterConfig.class);
    }

    @Override
    public GatewayFilter apply(FilterConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String accessToken = getAccessTokenFromHeader(request);

            if (accessToken == null) {
                return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }
            Map<String, Claim> claimMap;
            try {
                claimMap = getClaimsAndValidate(accessToken, config);
            } catch (RuntimeException e) {
                return this.onError(exchange, e.getMessage(), HttpStatus.FORBIDDEN);
            }
            ServerHttpRequest mutated = exchange.getRequest().mutate().header("X-User-Id", claimMap.get("user_id").asString()).build();
            return chain.filter(exchange.mutate().request(mutated).build());
        };
    }

    private String getAccessTokenFromHeader(ServerHttpRequest request) {
        List<String> authorizationHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        String authorization = Optional.ofNullable(authorizationHeaders)
                .map(header -> header.get(0))
                .orElse(null);
        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            return authorization.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        log.error(errorMessage);
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

    private Map<String, Claim> getClaimsAndValidate(String accessToken, FilterConfig config) {
        try {
            final DecodedJWT decodedJWT = JWT.decode(accessToken);
            final Algorithm algorithm = Algorithm.HMAC256(config.getSecretKey());
            algorithm.verify(decodedJWT);
            if (decodedJWT.getExpiresAt().before(Calendar.getInstance().getTime())) {
                throw new RuntimeException("Invalid Token");
            }
            return decodedJWT.getClaims();
        } catch (Exception e) {
            throw new RuntimeException("Invalid Token");
        }
    }

}
