package com.todo.app.todoservice.config;

import lombok.Data;

@Data
public class CouchbaseBucketProperty {
    private String name;
    private String userName;
    private String password;
}
