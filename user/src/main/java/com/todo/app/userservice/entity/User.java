package com.todo.app.userservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.index.CompositeQueryIndex;
import org.springframework.data.couchbase.core.index.CompositeQueryIndexes;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Getter
@Setter
@Builder
@Document
@CompositeQueryIndex(fields = {"email"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @Field(name="id")
    private String id;

    @QueryIndexed
    @Field(name = "user_id")
    private String userId;

    @Field(name = "name")
    private String name;

    @Field(name = "surname")
    private String surname;

    @QueryIndexed
    @Field(name = "email")
    private String email;

    @Field(name = "status")
    private String status;

    @Field(name = "password")
    private String password;
}
