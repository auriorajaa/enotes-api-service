package com.example.enotes.service;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.entity.Category;
import com.example.enotes.exception.ExistDataException;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.service.impl.CategoryServiceImpl;
import com.example.enotes.util.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private ModelMapper mapper;

    @Mock
    private Validation validation;

    private CategoryDto categoryDto = null;

    private Category category = null;

    private List<Category> categories = new ArrayList<>();

    private List<CategoryDto> categoriesDto = new ArrayList<>();

    @BeforeEach
    public void initialize() {
        categoryDto = CategoryDto.builder()
                .id(null)
                .name("Category from testing")
                .description("Description from testing")
                .isActive(true)
                .build();

        category = Category.builder()
                .id(null)
                .name("Category from testing")
                .description("Description from testing")
                .isActive(true)
                .isDeleted(false)
                .build();

        categories.add(category);
        categoriesDto.add(categoryDto);
    }

    @Test
    public void testSaveCategory() {
        // Arrange
        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(false);
        when(mapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        // Act
        Boolean saveCategory = categoryService.saveCategory(categoryDto);

        // Assert
        Assertions.assertTrue(saveCategory);

        // Verify
        verify(validation).categoryValidation(categoryDto);
        verify(categoryRepository).existsByName(categoryDto.getName());
        verify(categoryRepository).save(category);
    }

    @Test
    public void testCategoryExist() {
        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(true);

        ExistDataException exception = Assertions.assertThrows(ExistDataException.class, () -> {
            categoryService.saveCategory(categoryDto);
        });

        Assertions.assertEquals("Category already exist. Please try with another name.", exception.getMessage());

        verify(validation).categoryValidation(categoryDto);
        verify(categoryRepository).existsByName(categoryDto.getName());
        verify(categoryRepository, never()).save(category);
    }

    @Test
    public void testUpdateCategory() {
        categoryDto.setId(1);
        category.setId(1);

        // Arrange
        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(false);
        when(mapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        // Act
        Boolean saveCategory = categoryService.saveCategory(categoryDto);

        // Assert
        Assertions.assertTrue(saveCategory);

        // Verify
        verify(validation).categoryValidation(categoryDto);
        verify(categoryRepository).existsByName(categoryDto.getName());
        verify(categoryRepository).save(category);
    }

    @Test
    public void testGetAllCategory() {
        when(categoryRepository.findByIsDeletedFalse()).thenReturn(categories);
        List<CategoryDto> allCategory = categoryService.getAllCategory();

        Assertions.assertEquals(allCategory.size(), categories.size());
        verify(categoryRepository).findByIsDeletedFalse();
    }
}
