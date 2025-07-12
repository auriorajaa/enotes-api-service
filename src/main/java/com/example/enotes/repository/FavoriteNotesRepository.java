package com.example.enotes.repository;

import com.example.enotes.entity.FavoriteNotes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteNotesRepository extends JpaRepository<FavoriteNotes, Integer> {
    List<FavoriteNotes> findByUserId(int userId);
}
