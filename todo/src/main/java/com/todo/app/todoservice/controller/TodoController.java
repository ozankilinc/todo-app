package com.todo.app.todoservice.controller;

import com.todo.app.todoservice.model.request.TodoListRequest;
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
public class TodoController {

    private final TodoBusinessService todoBusinessService;

    @PostMapping
    public ResponseEntity<TodoListResponse> addTodoList(@RequestHeader("X-User-Id") String userId,
                                                        @RequestBody @Valid TodoListRequest request) {
        request.setUserId(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoBusinessService.createTodoList(request));
    }
}
