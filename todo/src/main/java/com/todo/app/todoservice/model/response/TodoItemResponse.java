package com.todo.app.todoservice.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemResponse {

    private String name;
    private String description;
    private String code;
}
