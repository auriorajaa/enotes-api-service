package com.example.enotes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.enotes.entity.Notes;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer>{

    Page<Notes> findByCreatedBy(Integer userId, Pageable pageable);
}
