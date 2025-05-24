package com.example.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.enotes.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
