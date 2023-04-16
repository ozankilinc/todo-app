package com.todo.app.todoservice.mapper.impl;

import com.todo.app.todoservice.entity.TodoItem;
import com.todo.app.todoservice.mapper.TodoItemMapper;
import com.todo.app.todoservice.model.request.TodoItemRequest;
import com.todo.app.todoservice.model.response.TodoItemResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TodoItemMapperImpl implements TodoItemMapper {

    private static final String TODO_ITEM_PREFIX = "TODO-ITEM-";

    @Override
    public TodoItem mapRequestToEntity(TodoItemRequest todoItemRequest) {
        Instant now = Instant.now();
        return TodoItem.builder()
                .itemCode(TODO_ITEM_PREFIX + now.getEpochSecond())
                .itemName(todoItemRequest.getItemName())
                .itemDescription(todoItemRequest.getItemDescription())
                .build();
    }

    @Override
    public TodoItemResponse mapEntityToResponse(TodoItem todoItem) {
        return TodoItemResponse.builder()
                .name(todoItem.getItemName())
                .code(todoItem.getItemCode())
                .description(todoItem.getItemDescription())
                .build();
    }
}
