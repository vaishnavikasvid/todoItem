package com.capstone.todobackend.service;

import com.capstone.todobackend.entity.TodoItem;

import java.util.List;

public interface TodoItemService {

    List<TodoItem> fetchAllTodoItems();
    TodoItem getTodoItemById(Long id);
    TodoItem createTodoItem(TodoItem todoItem);
    TodoItem removeTodoItemById(Long id);
    TodoItem updateTodoItem(TodoItem todoItem);
}
