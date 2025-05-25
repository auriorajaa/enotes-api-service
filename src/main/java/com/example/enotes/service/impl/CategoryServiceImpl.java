package com.example.enotes.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.CategoryResponse;
import com.example.enotes.entity.Category;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepo;

  @Autowired
  private ModelMapper mapper;

  @Override
  public Boolean saveCategory(CategoryDto categoryDto) {

    // Category category = new Category();
    // category.setName(categoryDto.getName());
    // category.setDescription(categoryDto.getDescription());
    // category.setIsActive(categoryDto.getIsActive());

    Category category = mapper.map(categoryDto, Category.class);

    category.setIsDeleted(false);
    category.setCreatedBy(1);

    Category saveCategory = categoryRepo.save(category);

    if (ObjectUtils.isEmpty(saveCategory)) {
      return false;
    }

    return true;
  }

  @Override
  public List<CategoryDto> getAllCategory() {
    List<Category> categories = categoryRepo.findAll();

    List<CategoryDto> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();

    return categoryDtoList;
  }

  @Override
  public List<CategoryResponse> getActiveCategory() {
    
    List<Category> categories = categoryRepo.findByIsActiveTrue();
    List<CategoryResponse> categoryList = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class)).toList();

    return categoryList;
  }

}
