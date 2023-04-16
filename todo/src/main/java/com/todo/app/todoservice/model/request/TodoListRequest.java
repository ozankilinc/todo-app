package com.todo.app.todoservice.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoListRequest {
    @NotBlank(message = "Name should not be blank")
    private String name;

    private String description;

    @JsonIgnore
    private String userId;
}
