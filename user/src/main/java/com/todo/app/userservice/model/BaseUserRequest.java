package com.todo.app.userservice.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserRequest {

    @NotBlank(message = "Username should not blank")
    @Email(message = "Username should be email")
    private String username;

    @NotBlank(message = "Password should not blank")
    private String password;
}
