package com.todo.app.todoservice.controller;

import com.todo.app.todoservice.model.request.TodoItemRequest;
import com.todo.app.todoservice.model.response.TodoListResponse;
import com.todo.app.todoservice.service.TodoBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo-list")
public class TodoItemController {

    private final TodoBusinessService todoBusinessService;

    @PostMapping("/{code}")
    public ResponseEntity<TodoListResponse> addTodoList(@RequestHeader("X-User-Id") String userId,
                                                        @PathVariable(name = "code") String code,
                                                        @RequestBody @Valid TodoItemRequest request) {
        request.setUserId(userId);
        request.setTodoListCode(code);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoBusinessService.addTodoItemToTodoList(request));
    }
}

