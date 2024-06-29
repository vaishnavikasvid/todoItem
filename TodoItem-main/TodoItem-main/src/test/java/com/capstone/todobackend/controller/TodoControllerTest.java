package com.capstone.todobackend.controller;

import com.capstone.todobackend.entity.TodoItem;
import com.capstone.todobackend.service.TodoItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoItemController.class)
@ActiveProfiles("test")
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoItemService todoItemService;

    @Autowired
    private ObjectMapper objectMapper;

    private TodoItem todoItem;

    @BeforeEach
    public void setUp() {
        todoItem = TodoItem.builder()
                .id(1L)
                .title("Test Todo")
                .description("This is a test todo.")
                .completed(false)
                .createdTime(LocalDateTime.now())
                .build();
    }

    @Test
    public void shouldFetchAllTodoItems() throws Exception {
        Mockito.when(todoItemService.fetchAllTodoItems()).thenReturn(Collections.singletonList(todoItem));

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value(todoItem.getTitle()));
    }

    @Test
    public void shouldCreateNewTodoItem() throws Exception {
        Mockito.when(todoItemService.createTodoItem(Mockito.any(TodoItem.class))).thenReturn(todoItem);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(todoItem.getTitle()));
    }

    @Test
    public void shouldFetchTodoItemById() throws Exception {
        Mockito.when(todoItemService.getTodoItemById(1L)).thenReturn(todoItem);

        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(todoItem.getTitle()));
    }

    @Test
    public void shouldUpdateTodoItem() throws Exception {
        Mockito.when(todoItemService.updateTodoItem(Mockito.any(TodoItem.class))).thenReturn(todoItem);

        mockMvc.perform(put("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(todoItem.getTitle()));
    }

    @Test
    public void shouldMarkTodoItemAsCompleted() throws Exception {
        todoItem.setCompleted(true);
        Mockito.when(todoItemService.getTodoItemById(1L)).thenReturn(todoItem);
        Mockito.when(todoItemService.updateTodoItem(Mockito.any(TodoItem.class))).thenReturn(todoItem);

        mockMvc.perform(patch("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void shouldDeleteTodoItemById() throws Exception {
        Mockito.when(todoItemService.removeTodoItemById(1L)).thenReturn(todoItem);

        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
public void shouldReturnTotalCountOfTodoItems() throws Exception {
    // Prepare a list with one todo item
    List<TodoItem> todoItems = Collections.singletonList(todoItem);
    
    // Mock the fetchAllTodoItems() method to return the list
    Mockito.when(TodoService.fetchAllTodos()).thenReturn(todoItems);

    // Perform the GET request to "/todos/count" endpoint
    mockMvc.perform(get("/todos/count"))
            .andExpect(status().isOk()) // Assert HTTP status 200 OK
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Assert response content type JSON
            .andExpect(content().string("1")); // Assert that the response body is "1"
}
}
