package com.todo.app.userservice.generator.impl;

import com.todo.app.userservice.generator.UserIdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserIdGeneratorImpl implements UserIdGenerator {
    @Override
    public String generateUserId() {
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
}
