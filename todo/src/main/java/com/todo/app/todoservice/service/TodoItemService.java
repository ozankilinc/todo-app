package com.todo.app.todoservice.service;

import com.todo.app.todoservice.entity.TodoItem;

public interface TodoItemService {
    boolean existTodoItemByItemNameAndCode(String itemName, String itemCode);

    void saveTodoItem(TodoItem todoItem);
}
