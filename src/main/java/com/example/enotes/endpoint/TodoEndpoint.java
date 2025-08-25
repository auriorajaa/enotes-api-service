package com.example.enotes.endpoint;

import com.example.enotes.dto.TodoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getAllTodoByUser() throws Exception;

}
