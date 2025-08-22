package com.example.enotes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.enotes.entity.Notes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer>{

    Page<Notes> findByCreatedBy(Integer userId, Pageable pageable);

    List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);

    Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pageable);

    List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate);

    @Query("""
    SELECT n FROM Notes n
    WHERE 
        (
            lower(n.title) LIKE lower(CONCAT('%', :keyword, '%'))
            OR lower(n.description) LIKE lower(CONCAT('%', :keyword, '%'))
            OR lower(n.category.name) LIKE lower(CONCAT('%', :keyword, '%'))
        )
        AND n.isDeleted = false
        AND n.createdBy = :userId
    """)
    Page<Notes> searchNotes(@Param("keyword") String keyword,
                            @Param("userId") Integer userId,
                            Pageable pageable);


}
