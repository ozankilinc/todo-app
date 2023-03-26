package com.todo.app.todoservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo-list")
public class TodoController {

    @GetMapping("/test")
    public ResponseEntity<?> addTodoList(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok().body(userId);

    }
}
