package com.example.enotes.service.impl;

import com.example.enotes.dto.TodoDto;
import com.example.enotes.entity.Todo;
import com.example.enotes.enums.TodoStatus;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.TodoRepository;
import com.example.enotes.service.TodoService;
import com.example.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;

    @Override
    public Boolean saveTodo(TodoDto todoDto) throws Exception{
        // Validation to do list status
        validation.todoValidation(todoDto);

        Todo todo = mapper.map(todoDto, Todo.class);
        todo.setStatusId(todo.getStatusId());
        Todo saveTodo = todoRepo.save(todo);

        if (!ObjectUtils.isEmpty(saveTodo)) {
            return true;
        }

        return false;
    }

    @Override
    public TodoDto getTodoById(Integer id) throws Exception{
        Todo todo = todoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found"));

        TodoDto todoDto = mapper.map(todo, TodoDto.class);
        setStatus(todoDto, todo);

        return todoDto;
    }

    private void setStatus(TodoDto todoDto, Todo todo) {
        for (TodoStatus st : TodoStatus.values()) {
            if (st.getId().equals(todo.getStatusId())) {
                TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder()
                        .id(st.getId())
                        .name(st.getName())
                        .build();

                todoDto.setStatus(statusDto);
            }
        }
    }

    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId = 2;

        List<Todo> todos = todoRepo.findByCreatedBy(userId);

        return todos.stream().map(todo -> {
            TodoDto todoDto = mapper.map(todo, TodoDto.class);
            setStatus(todoDto, todo);

            return todoDto;
        }).toList();
    }
}
