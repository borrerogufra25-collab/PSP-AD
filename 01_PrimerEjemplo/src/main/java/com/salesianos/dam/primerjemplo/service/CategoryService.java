package com.salesianos.dam.primerjemplo.service;


import com.salesianos.dam.primerjemplo.error.CategoryNotFoundException;
import com.salesianos.dam.primerjemplo.model.Category;
import com.salesianos.dam.primerjemplo.repo.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public List<Category> getAll() {
    List<Category> categories = categoryRepository.findAll();
    if (categories.isEmpty()) {
      throw new CategoryNotFoundException();
    }

    return categories;
  }

  public Category getById(Long id) {
    return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
  }

  public Category addCategory(Category category) {
    return categoryRepository.save(category);
  }

  public Category updateCategory(Long id, Category category) {
    Category categoryToUpdate = getById(id);

    if (!categoryRepository.existsById(id)) {
      throw new CategoryNotFoundException();
    }
    categoryToUpdate.setName(category.getName());
    return categoryRepository.save(categoryToUpdate);
  }

  public void deleteCategory(Long id) {

    if (!categoryRepository.existsById(id)) {
      throw new CategoryNotFoundException();
    }
    categoryRepository.deleteById(id);
  }

}
