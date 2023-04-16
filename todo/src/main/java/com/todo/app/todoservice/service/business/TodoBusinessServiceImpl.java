package com.todo.app.todoservice.service.business;

import com.todo.app.todoservice.entity.TodoItem;
import com.todo.app.todoservice.entity.TodoList;
import com.todo.app.todoservice.mapper.TodoItemMapper;
import com.todo.app.todoservice.mapper.TodoListMapper;
import com.todo.app.todoservice.model.request.TodoItemRequest;
import com.todo.app.todoservice.model.request.TodoListRequest;
import com.todo.app.todoservice.model.response.TodoListResponse;
import com.todo.app.todoservice.service.TodoBusinessService;
import com.todo.app.todoservice.service.TodoItemService;
import com.todo.app.todoservice.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoBusinessServiceImpl implements TodoBusinessService {

    private final TodoService todoService;
    private final TodoItemService todoItemService;
    private final TodoListMapper todoListMapper;
    private final TodoItemMapper todoItemMapper;


    @Override
    public TodoListResponse createTodoList(TodoListRequest request) {
        TodoList todoList = todoService.upsertTodoList(todoListMapper.mapRequestToEntity(request));
        return todoListMapper.mapEntityToResponse(todoList);
    }

    @Override
    public TodoListResponse addTodoItemToTodoList(TodoItemRequest request) {
        boolean test = todoItemService.existTodoItemByItemNameAndCode(request.getItemName(), request.getTodoListCode());
        log.info("Todo Item isExist : " + test);
        TodoItem todoItem = todoItemMapper.mapRequestToEntity(request);
        todoItemService.saveTodoItem(todoItem);
        TodoList todoList = todoService.findByUserIdAndCode(request.getUserId(), request.getTodoListCode());
        todoList.addTodoItem(todoItem);
        return todoListMapper.mapEntityToResponse(todoService.upsertTodoList(todoList));
    }
}
