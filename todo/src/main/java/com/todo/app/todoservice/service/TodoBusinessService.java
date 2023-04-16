package com.todo.app.todoservice.service;

import com.todo.app.todoservice.model.request.TodoItemRequest;
import com.todo.app.todoservice.model.request.TodoListRequest;
import com.todo.app.todoservice.model.response.TodoListResponse;

public interface TodoBusinessService {

    TodoListResponse createTodoList(TodoListRequest request);

    TodoListResponse addTodoItemToTodoList(TodoItemRequest request);
}
