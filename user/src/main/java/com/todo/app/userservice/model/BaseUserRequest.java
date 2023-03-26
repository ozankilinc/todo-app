package com.todo.app.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseUserRequest {

    @NotBlank(message = "Username should not blank")
    @Email(message = "Username should be email")
    private String username;

    @NotBlank(message = "Password should not blank")
    private String password;
}
