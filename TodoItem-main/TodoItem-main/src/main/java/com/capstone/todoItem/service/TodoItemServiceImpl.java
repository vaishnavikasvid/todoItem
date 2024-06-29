package com.capstone.todobackend.service;

import com.capstone.todobackend.entity.TodoItem;
import com.capstone.todobackend.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoItemServiceImpl implements TodoItemService {

    private final TodoItemRepository todoItemRepository;

    @Autowired
    public TodoItemServiceImpl(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @Override
    public List<TodoItem> fetchAllTodoItems() {
        return todoItemRepository.findAll();
    }

    @Override
    public TodoItem getTodoItemById(Long id) {
        Optional<TodoItem> optionalTodoItem = (Optional<TodoItem>) todoItemRepository.findById(id);
        return optionalTodoItem.orElse(null); // orElse(null) can be simplified to just orElse()
    }
    

    @Override
    public TodoItem createTodoItem(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }

    @Override
    public TodoItem removeTodoItemById(Long id) {
        TodoItem todoItem = getTodoItemById(id);
        if (todoItem != null) {
            todoItemRepository.delete(todoItem);
        }
        return todoItem;
    }

    @Override
    public TodoItem updateTodoItem(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }
}
