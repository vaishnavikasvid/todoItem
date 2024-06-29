package com.capstone.todobackend.controller;

import com.capstone.todobackend.entity.TodoItem;
import com.capstone.todobackend.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoItemController {

    private final TodoItemService todoItemService;

    @Autowired
    public TodoItemController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @GetMapping
    public ResponseEntity<List<TodoItem>> retrieveAllTodoItems() {
        List<TodoItem> todoItems = todoItemService.fetchAllTodoItems();
        return ResponseEntity.ok(todoItems);
    }

    @PostMapping
    public ResponseEntity<TodoItem> addNewTodoItem(@RequestBody TodoItem todoItem) {
        TodoItem newTodoItem = todoItemService.createTodoItem(todoItem);
        return ResponseEntity.ok(newTodoItem);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoItem> fetchTodoItemById(@PathVariable Long todoId) {
        TodoItem todoItem = todoItemService.getTodoItemById(todoId);
        return todoItem != null ? ResponseEntity.ok(todoItem) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoItem> modifyTodoItem(@PathVariable Long todoId, @RequestBody TodoItem modifiedTodoItem) {
        modifiedTodoItem.setId(todoId);
        TodoItem updatedTodoItem = todoItemService.updateTodoItem(modifiedTodoItem);
        return updatedTodoItem != null ? ResponseEntity.ok(updatedTodoItem) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoItem> setTodoItemAsCompleted(@PathVariable Long todoId) {
        TodoItem todoItem = todoItemService.getTodoItemById(todoId);
        if (todoItem != null) {
            todoItem.setCompleted(true);
            TodoItem updatedTodoItem = todoItemService.updateTodoItem(todoItem);
            return ResponseEntity.ok(updatedTodoItem);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> removeTodoItemById(@PathVariable Long todoId) {
        TodoItem todoItem = todoItemService.removeTodoItemById(todoId);
        return todoItem != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTodoItemCount() {
        long count = todoItemService.fetchAllTodoItems().size();
        return ResponseEntity.ok(count);
    }
}
