package com.example.enotes.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.exception.ValidationException;

@Component
public class Validation {

	public void categoryValidation(CategoryDto categoryDto) {

		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category Object/JSON must not be null or blank.");
		} else {

			// Validation for name field
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "Category name field must not be blank.");
			} else {
				if (categoryDto.getName().length() < 3) {
					error.put("name", "Category name must be between 3 and 255 characters long.");
				}
				if (categoryDto.getName().length() > 255) {
					error.put("name", "Category name must not exceed 255 characters long.");
				}
			}

			// Validation for description field
			if (categoryDto.getDescription().length() > 1000) {
				error.put("description", "Category description must not exceed 1000 characters.");
			}

			// Validation for isActive
			if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("isActive", "isActive field must not be blank.");
			} else {
				if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue()
						&& categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
					error.put("isActive", "Invalid value for isActive field.");
				}
			}
		}

		if (!error.isEmpty()) {
			throw new ValidationException(error);
		}

	}

}
