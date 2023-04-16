package com.todo.app.todoservice.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoItemRequest {

    @NotBlank(message = "Item Name Should Not Be Blank")
    private String itemName;
    private String itemDescription;

    @JsonIgnore
    private String todoListCode;
    @JsonIgnore
    private String userId;
}
