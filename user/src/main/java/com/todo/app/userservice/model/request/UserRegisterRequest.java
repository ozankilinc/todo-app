package com.todo.app.userservice.model.request;

import com.todo.app.userservice.model.BaseUserRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest extends BaseUserRequest {

    @NotBlank(message = "Name should not blank")
    private String name;
    @NotBlank(message = "Surname should not blank")
    private String surname;
}
