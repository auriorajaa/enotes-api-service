package com.example.enotes.endpoint;

import com.example.enotes.dto.TodoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.enotes.util.Constants.ROLE_USER;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @PostMapping("/")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;

    @GetMapping("/list")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getAllTodoByUser() throws Exception;

}
