package com.example.enotes.service;

import java.util.List;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.CategoryResponse;

public interface CategoryService {
  
  public Boolean saveCategory(CategoryDto categoryDto);

  public List<CategoryDto> getAllCategory();

  public List<CategoryResponse> getActiveCategory();
}
