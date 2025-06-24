package com.example.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.enotes.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer>{
  
}
