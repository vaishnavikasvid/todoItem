package com.capstone.todobackend.repository;

import com.capstone.todobackend.entity.TodoItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TodoItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public long saveOrUpdate(TodoItem todoItem) {
        return entityManager.merge(todoItem).getId();
    }

    public List<TodoItem> findAll() {
        return null;
    }

    public Object findById(Long id) {
        return null;
    }

    public TodoItem save(TodoItem todoItem) {
        return null;
    }

    public void delete(TodoItem todoItem) {
    }
}
