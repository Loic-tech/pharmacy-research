package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.Category;
import com.search.pharmacy.repository.CategoryRepository;
import com.search.pharmacy.ws.mapper.CategoryMapper;
import com.search.pharmacy.ws.model.CategoryDTO;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public CategoryDTO create(CategoryDTO categoryDTO) {
    return Optional.of(categoryDTO)
        .map(categoryMapper::toEntity)
        .map(categoryRepository::save)
        .map(categoryMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("Could not create a new category"));
  }

  public List<CategoryDTO> getCategories() {
    return categoryRepository.findAll().stream().map(categoryMapper::toDTO).toList();
  }

  public CategoryDTO getCategory(Long id) {
    return categoryRepository.findById(id).map(categoryMapper::toDTO).orElseThrow();
  }

  public CategoryDTO update(Long id, Map<String, Object> fields) {
    Optional<Category> optionalCategory = categoryRepository.findById(id);
    if (optionalCategory.isPresent()) {
      fields.forEach(
          (key, value) -> {
            Field field = ReflectionUtils.findField(Category.class, key);
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, optionalCategory.get(), value);
          });
      return categoryMapper.toDTO(categoryRepository.save(optionalCategory.get()));
    }
    return null;
  }

  public void delete(Long id) {
    categoryRepository.deleteById(id);
  }
}
