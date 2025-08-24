package com.dailycodework.lib.controller;

import com.dailycodework.lib.dto.AuthorDto;
import com.dailycodework.lib.dto.CategoryDto;
import com.dailycodework.lib.entity.Author;
import com.dailycodework.lib.entity.Category;
import com.dailycodework.lib.mapper.CategoryMapper;
import com.dailycodework.lib.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> dtos = categoryService.findAllCategories().stream().map(categoryMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }



    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id){
        return categoryService.findById(id)
                .map(categoryMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() ->ResponseEntity.notFound().build());
    }


    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.save(categoryMapper.toEntity(categoryDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.toDto(category));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updatedCategory(@PathVariable("id") Long id , @RequestBody CategoryDto categoryDto){
        Category category =categoryMapper.toEntity(categoryDto);
        category.setId(id);
        Category updated = categoryService.update(id ,category);
        return ResponseEntity.ok(categoryMapper.toDto(updated));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
