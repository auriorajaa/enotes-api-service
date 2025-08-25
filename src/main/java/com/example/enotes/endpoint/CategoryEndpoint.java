package com.example.enotes.endpoint;

import com.example.enotes.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.enotes.util.Constants.ROLE_ADMIN;
import static com.example.enotes.util.Constants.ROLE_ANY;

@Tag(name = "Category", description = "Notes category related API's")
@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @Operation(summary = "Add new category for ADMIN endpoint")
    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

    @Operation(summary = "Get all category endpoint")
    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllCategory();

    @Operation(summary = "Get all active category endpoint")
    @GetMapping("/active")
    @PreAuthorize(ROLE_ANY)
    ResponseEntity<?> getActiveCategory();

    @Operation(summary = "Get details category by ID endpoint")
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Delete specific category by ID endpoint")
    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> deleteCategoryById(@PathVariable Integer id);

}
