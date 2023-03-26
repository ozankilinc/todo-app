package com.todo.app.gatewayserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "filter.config")
public class FilterConfig {
    private String secretKey;
    private String bearerPrefix;
}
