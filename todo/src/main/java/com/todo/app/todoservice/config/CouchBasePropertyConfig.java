package com.todo.app.todoservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "couch.base")
public class CouchBasePropertyConfig {

    private String connection;
    private String username;
    private String password;
    private String bucketName;
}
