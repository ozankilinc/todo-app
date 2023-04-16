package com.todo.app.todoservice.service.impl;

import com.todo.app.todoservice.entity.TodoList;
import com.todo.app.todoservice.repository.TodoListRepository;
import com.todo.app.todoservice.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoListRepository todoListRepository;

    @Override
    public TodoList upsertTodoList(TodoList todoList) {
        return todoListRepository.save(todoList);
    }

    @Override
    public TodoList findByUserIdAndCode(String userId, String code) {
        return todoListRepository.findByUserIdAndCode(userId, code)
                .orElseThrow(() -> new RuntimeException("TodoList Not Found"));
    }
}
