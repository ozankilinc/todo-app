package com.todo.app.todoservice.model.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoListResponse {

    private String name;
    private String description;
    private String code;
    @Builder.Default
    private List<TodoItemResponse> todoItemResponses = new ArrayList<>();
}
