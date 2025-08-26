package com.example.enotes.controller;

import java.util.List;

import com.example.enotes.endpoint.CategoryEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.CategoryResponse;
import com.example.enotes.service.CategoryService;
import com.example.enotes.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CategoryController implements CategoryEndpoint {

	@Autowired
	private CategoryService categoryService;

    @Override
	public ResponseEntity<?> saveCategory(CategoryDto categoryDto) {

		Boolean saveCategory = categoryService.saveCategory(categoryDto);

		if (saveCategory) {
			return CommonUtil.createBuildResponseMessage("Category successfully saved.", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponseMessage("Something went wrong when saving category. Failed to save.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @Override
    public ResponseEntity<?> getAllCategory() {

		List<CategoryDto> allCategory = categoryService.getAllCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}

    @Override
    public ResponseEntity<?> getActiveCategory() {

		List<CategoryResponse> allCategory = categoryService.getActiveCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}

    @Override
    public ResponseEntity<?> getCategoryDetailsById(Integer id) throws Exception {

		CategoryDto categoryDto = categoryService.getCategoryById(id);

		if (ObjectUtils.isEmpty(categoryDto)) {
			return CommonUtil.createErrorResponseMessage("Category not found with ID: " + id, HttpStatus.NOT_FOUND);
		}

		return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);
	}

    @Override
    public ResponseEntity<?> deleteCategoryById(Integer id) {

		Boolean deleted = categoryService.deleteCategory(id);

		if (deleted) {
			return CommonUtil.createBuildResponseMessage("Successfully deleting category.", HttpStatus.OK);
		}

		return CommonUtil.createErrorResponseMessage("Something went wrong. Failed to delete category.",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
