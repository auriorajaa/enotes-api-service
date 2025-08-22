package com.example.enotes.service;

import com.example.enotes.dto.TodoDto;

import java.util.List;

public interface TodoService {

    public Boolean saveTodo(TodoDto todo) throws Exception;

    public TodoDto getTodoByIdAndUser(Integer todoId, Integer userId) throws Exception;

    public List<TodoDto> getTodoByUser();
}
