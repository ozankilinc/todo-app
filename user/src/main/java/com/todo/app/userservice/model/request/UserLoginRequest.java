package com.todo.app.userservice.model.request;

import com.todo.app.userservice.model.BaseUserRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
public class UserLoginRequest extends BaseUserRequest {
}
