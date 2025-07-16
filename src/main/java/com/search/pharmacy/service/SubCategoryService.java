package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.SubCategory;
import com.search.pharmacy.repository.SubCategoryRepository;
import com.search.pharmacy.ws.mapper.SubCategoryMapper;
import com.search.pharmacy.ws.model.SubCategoryDTO;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class SubCategoryService {

  private final SubCategoryRepository subCategoryRepository;
  private final SubCategoryMapper subCategoryMapper;

  @Transactional
  public SubCategoryDTO create(SubCategoryDTO subCategoryDTO) {
    return Optional.of(subCategoryDTO)
        .map(subCategoryMapper::toEntity)
        .map(subCategoryRepository::save)
        .map(subCategoryMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("Could not create a new sub category"));
  }

  public List<SubCategoryDTO> getSubCategories() {
    return subCategoryRepository.findAll().stream().map(subCategoryMapper::toDTO).toList();
  }

  public SubCategoryDTO getSubCategory(Long id) {
    return subCategoryRepository.findById(id).map(subCategoryMapper::toDTO).orElseThrow();
  }

  public SubCategoryDTO update(Long id, Map<String, Object> fields) {
    Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(id);
    if (optionalSubCategory.isPresent()) {
      fields.forEach(
          (key, value) -> {
            Field field = ReflectionUtils.findField(SubCategory.class, key);
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, optionalSubCategory.get(), value);
          });
      return subCategoryMapper.toDTO(subCategoryRepository.save(optionalSubCategory.get()));
    }
    return null;
  }

  @Transactional
  public void delete(Long id) {
    subCategoryRepository.deleteById(id);
  }
}
