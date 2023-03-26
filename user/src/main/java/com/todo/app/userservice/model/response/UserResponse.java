package com.todo.app.userservice.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String username;
    private String name;
    private String surname;
    private String userId;
}
