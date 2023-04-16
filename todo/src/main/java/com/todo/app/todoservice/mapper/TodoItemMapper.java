package com.todo.app.todoservice.mapper;

import com.todo.app.todoservice.entity.TodoItem;
import com.todo.app.todoservice.model.request.TodoItemRequest;
import com.todo.app.todoservice.model.response.TodoItemResponse;

public interface TodoItemMapper {

    TodoItem mapRequestToEntity(TodoItemRequest todoItemRequest);

    TodoItemResponse mapEntityToResponse(TodoItem todoItem);
}
