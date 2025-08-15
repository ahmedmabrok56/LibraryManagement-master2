package service;

import entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> findAllCategories();
    Optional<Category> findById(Long id);
    Category save(Category category);
    Category update(Long id, Category categoryDetails);
    void deleteById(Long id);
}
