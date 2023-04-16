package com.todo.app.todoservice.mapper.impl;

import com.todo.app.todoservice.entity.TodoList;
import com.todo.app.todoservice.mapper.TodoItemMapper;
import com.todo.app.todoservice.mapper.TodoListMapper;
import com.todo.app.todoservice.model.request.TodoListRequest;
import com.todo.app.todoservice.model.response.TodoItemResponse;
import com.todo.app.todoservice.model.response.TodoListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TodoListMapperImpl implements TodoListMapper {

    private final TodoItemMapper todoItemMapper;
    private static final String TODO_LIST_CODE_PREFIX = "TODO-LIST-";

    @Override
    public TodoList mapRequestToEntity(TodoListRequest todoListRequest) {
        Instant now = Instant.now();
        return TodoList.builder()
                .name(todoListRequest.getName())
                .description(todoListRequest.getDescription())
                .code(TODO_LIST_CODE_PREFIX + now.getEpochSecond())
                .userId(todoListRequest.getUserId())
                .build();
    }

    @Override
    public TodoListResponse mapEntityToResponse(TodoList todoList) {
        List<TodoItemResponse> todoItemResponseList = todoList.getTodoItems().stream()
                .map(todoItemMapper::mapEntityToResponse)
                .collect(Collectors.toList());
        return TodoListResponse.builder()
                .code(todoList.getCode())
                .description(todoList.getDescription())
                .name(todoList.getName())
                .todoItemResponses(todoItemResponseList)
                .build();
    }
}
