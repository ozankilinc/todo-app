package com.todo.app.userservice.model.request;

import com.todo.app.userservice.model.BaseUserRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserRegisterRequest extends BaseUserRequest {

    @NotBlank(message = "Name should not blank")
    private String name;
    @NotBlank(message = "Surname should not blank")
    private String surname;
}
