package com.todo.app.todoservice.service.impl;

import com.todo.app.todoservice.entity.TodoItem;
import com.todo.app.todoservice.repository.TodoItemRepository;
import com.todo.app.todoservice.service.TodoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoItemServiceImpl implements TodoItemService {
    private final TodoItemRepository todoItemRepository;
    @Override
    public boolean existTodoItemByItemNameAndCode(String itemName, String itemCode) {
        return todoItemRepository.existsByItemNameAndItemCode(itemName,itemCode);
    }

    @Override
    public void saveTodoItem(TodoItem todoItem) {
        todoItemRepository.save(todoItem);
    }
}
