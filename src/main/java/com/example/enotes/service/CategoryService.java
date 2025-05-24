package com.example.enotes.service;

import java.util.List;

import com.example.enotes.entity.Category;

public interface CategoryService {
  
  public Boolean saveCategory(Category category);

  public List<Category> getAllCategory();
}
