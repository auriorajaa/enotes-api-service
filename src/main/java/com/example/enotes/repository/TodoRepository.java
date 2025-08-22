package com.example.enotes.repository;

import com.example.enotes.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByCreatedBy(Integer userId);
    Optional<Todo> findByIdAndCreatedBy(Integer id, Integer createdBy);
}
