package com.todo.app.todoservice.repository;

import com.todo.app.todoservice.entity.TodoItem;
import org.springframework.data.repository.CrudRepository;

public interface TodoItemRepository extends CrudRepository<TodoItem, String> {

    boolean existsByItemNameAndItemCode(String itemName, String itemCode);
}
