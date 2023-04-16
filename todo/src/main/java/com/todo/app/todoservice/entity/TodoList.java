package com.todo.app.todoservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Document
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @Field(name="id")
    private String id;

    @QueryIndexed
    @Field(name = "user_id")
    private String userId;

    @Field(name = "name")
    private String name;

    @Field(name = "description")
    private String description;

    @Field(name = "code")
    @QueryIndexed
    private String code;

    @Builder.Default
    @Field(name = "todo_items")
    private List<TodoItem> todoItems = new ArrayList<>();

    public void addTodoItem(TodoItem todoItem) {
        this.todoItems.add(todoItem);
    }

}
