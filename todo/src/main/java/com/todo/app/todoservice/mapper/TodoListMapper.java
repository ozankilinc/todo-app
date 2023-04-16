package com.todo.app.todoservice.mapper;

import com.todo.app.todoservice.entity.TodoList;
import com.todo.app.todoservice.model.request.TodoListRequest;
import com.todo.app.todoservice.model.response.TodoListResponse;

public interface TodoListMapper {

    TodoList mapRequestToEntity(TodoListRequest todoListRequest);

    TodoListResponse mapEntityToResponse(TodoList todoList);
}
