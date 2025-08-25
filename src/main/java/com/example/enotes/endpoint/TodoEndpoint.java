package com.example.enotes.endpoint;

import com.example.enotes.dto.TodoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.enotes.util.Constants.ROLE_USER;

@Tag(name = "To Do List", description = "To do list related API's")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @Operation(summary = "Save to do list endpoint")
    @PostMapping("/")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;

    @Operation(summary = "Get to do list by ID endpoint")
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get all user to do list endpoint")
    @GetMapping("/list")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getAllTodoByUser() throws Exception;

}
