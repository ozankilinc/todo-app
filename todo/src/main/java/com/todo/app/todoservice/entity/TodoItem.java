package com.todo.app.todoservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Getter
@Setter
@Builder
@Document
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @Field(name="id")
    private String id;

    @QueryIndexed
    @Field(name = "item_name")
    private String itemName;

    @Field(name = "item_description")
    private String itemDescription;

    @QueryIndexed
    @Field(name = "item_code")
    private String itemCode;

}
