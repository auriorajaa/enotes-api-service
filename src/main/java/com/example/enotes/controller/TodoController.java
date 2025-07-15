package com.example.enotes.controller;

import com.example.enotes.dto.TodoDto;
import com.example.enotes.service.TodoService;
import com.example.enotes.util.CommonUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception {
        Boolean saveTodo = todoService.saveTodo(todo);

        if (saveTodo) {
            return CommonUtil.createBuildResponseMessage("Todo successfully created.", HttpStatus.CREATED);
        } else {
            return CommonUtil.createErrorResponseMessage("Todo failed to create.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> saveTodo(@PathVariable Integer id) throws Exception {
        TodoDto todo = todoService.getTodoById(id);

        return CommonUtil.createBuildResponse(todo, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllTodoByUser() throws Exception {
        List<TodoDto> todoList = todoService.getTodoByUser();

        if (CollectionUtils.isEmpty(todoList)) {
            return ResponseEntity.noContent().build();
        }

        return CommonUtil.createBuildResponse(todoList, HttpStatus.OK);
    }
}
