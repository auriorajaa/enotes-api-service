package com.example.enotes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.enotes.entity.Category;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepo;

  @Override
  public Boolean saveCategory(Category category) {
    category.setIsDeleted(false);
    category.setCreatedBy(1);

    Category saveCategory = categoryRepo.save(category);

    if (ObjectUtils.isEmpty(saveCategory)) {
      return false;
    }

    return true;
  }

  @Override
  public List<Category> getAllCategory() {
    List<Category> categories = categoryRepo.findAll();

    return categories;
  }

}
