package com.todo.app.todoservice.repository;

import com.todo.app.todoservice.entity.TodoList;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TodoListRepository extends CrudRepository<TodoList, String> {

    Optional<TodoList> findByUserIdAndCode(String userId, String code);

    boolean existsByUserIdAndCode(String userId, String code);
}
