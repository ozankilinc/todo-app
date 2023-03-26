package com.todo.app.userservice.handler;

import com.todo.app.userservice.model.exception.TodoAppApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = TodoAppApiException.class)
    public ResponseEntity<Object> handleTodoAppApiException(TodoAppApiException ex) {
        log.error("TodoAppApiException Occurred ", ex);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errors", ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        log.error("BindException Occurred ", ex);
        return getErrorResponseForBindException(ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        log.error("MethodArgumentNotValidException ", ex);
        return getErrorResponseForBindException(ex);
    }

    private ResponseEntity<Object> getErrorResponseForBindException(BindException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> errorMap = errors.stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        Map<String, Map<String, String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errorMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
