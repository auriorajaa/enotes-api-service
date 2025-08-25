package com.example.enotes.controller;

import com.example.enotes.dto.TodoDto;
import com.example.enotes.endpoint.TodoEndpoint;
import com.example.enotes.service.TodoService;
import com.example.enotes.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TodoController implements TodoEndpoint {

    @Autowired
    private TodoService todoService;

    @Override
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception {
        Boolean saveTodo = todoService.saveTodo(todo);

        if (saveTodo) {
            return CommonUtil.createBuildResponseMessage("Todo successfully created.", HttpStatus.CREATED);
        } else {
            return CommonUtil.createErrorResponseMessage("Todo failed to create.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception {
        Integer userId = CommonUtil.getLoggedInUser().getId();

        TodoDto todo = todoService.getTodoByIdAndUser(id, userId);

        return CommonUtil.createBuildResponse(todo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllTodoByUser() throws Exception {
        List<TodoDto> todoList = todoService.getTodoByUser();

        if (CollectionUtils.isEmpty(todoList)) {
            return ResponseEntity.noContent().build();
        }

        return CommonUtil.createBuildResponse(todoList, HttpStatus.OK);
    }
}
