package com.todo.app.todoservice.service;

import com.todo.app.todoservice.entity.TodoList;

public interface TodoService {

    TodoList upsertTodoList(TodoList todoList);

    TodoList findByUserIdAndCode(String userId, String code);
}
