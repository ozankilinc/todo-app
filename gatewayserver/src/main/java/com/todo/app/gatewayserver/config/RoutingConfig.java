package com.todo.app.gatewayserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class RoutingConfig {

    private final FilterConfig filterConfig;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, AuthFilter authFilter) {
        return builder.routes()
                .route(p -> p
                        .path("/user/**")
                        .filters(f -> f.rewritePath("/user/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time",new Date().toString()))
                        .uri("lb://USER"))
                .route(p -> p
                        .path("/todo/**")
                        .filters(f -> f.rewritePath("/todo/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time",new Date().toString())
                                .filter(authFilter.apply(filterConfig))
                        )
                        .uri("lb://TODO"))
                .build();
    }
}
