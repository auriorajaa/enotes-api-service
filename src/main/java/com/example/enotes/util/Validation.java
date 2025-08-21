package com.example.enotes.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.enotes.dto.TodoDto;
import com.example.enotes.dto.UserRequest;
import com.example.enotes.enums.TodoStatus;
import com.example.enotes.exception.ExistDataException;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.RoleRepository;
import com.example.enotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.exception.ValidationException;
import org.springframework.util.StringUtils;

@Component
public class Validation {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UserRepository userRepo;

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

	public void todoValidation(TodoDto todo) throws Exception{
		TodoDto.StatusDto reqStatus = todo.getStatus();

		Boolean statusFound = false;

		for (TodoStatus st : TodoStatus.values()) {
			if (st.getId().equals(reqStatus.getId())) {
				statusFound = true;
			}
		}

		if (!statusFound) {
			throw new ResourceNotFoundException("Invalid status");
		}
	}

	public void userValidation(UserRequest userRequest) {
		if (!StringUtils.hasText(userRequest.getFirstName())) {
			throw new IllegalArgumentException("First name must not be blank.");
		}

		if (!StringUtils.hasText(userRequest.getLastName())) {
			throw new IllegalArgumentException("Last name must not be blank.");
		}

		if (!StringUtils.hasText(userRequest.getEmail()) ||
				!userRequest.getEmail().matches(Constants.EMAIL_REGEX)) {
			throw new IllegalArgumentException("Invalid email address");
		} else {
			// Validate email already exist
			Boolean existEmail = userRepo.existsByEmail(userRequest.getEmail());

			if (existEmail) {
				throw new ExistDataException("Email already exist");
			}
		}

		if (!StringUtils.hasText(userRequest.getPassword()) ||
				!userRequest.getPassword().matches(Constants.PASSWORD_REGEX)) {
			throw new IllegalArgumentException("Password must be at least 8 characters and include uppercase, lowercase, number, and special character.");
		}

		if (!StringUtils.hasText(userRequest.getMobNo()) ||
				!userRequest.getMobNo().matches(Constants.MOB_NO_REGEX)) {
			throw new IllegalArgumentException("Invalid mobile number. Format must include country code. Example: +14155552671 (US).");
		}

		if (CollectionUtils.isEmpty(userRequest.getRoles())) {
			throw new IllegalArgumentException("Roles must not be blank.");
		} else {
			List<Integer> roleIds = roleRepo
					.findAll()
					.stream()
					.map(r -> r.getId())
					.toList();

			List<Integer> invalidReqRoleIds = userRequest
					.getRoles()
					.stream()
					.map(r -> r.getId())
					.filter(roleId -> !roleIds.contains(roleId))
					.toList();

			if (!CollectionUtils.isEmpty(invalidReqRoleIds)) {
				throw new IllegalArgumentException("Invalid role ids: " + invalidReqRoleIds);
			}
		}
	}
}
