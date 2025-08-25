package com.example.enotes.endpoint;

import com.example.enotes.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getAllCategory();

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<?> getActiveCategory();

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteCategoryById(@PathVariable Integer id);

}
