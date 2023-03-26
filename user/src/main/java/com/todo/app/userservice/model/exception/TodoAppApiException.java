package com.todo.app.userservice.model.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoAppApiException extends RuntimeException{

    private int httpStatus;
    private String message;

    public TodoAppApiException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
