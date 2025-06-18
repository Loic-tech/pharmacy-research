package com.search.pharmacy.ws.controller;

import com.search.pharmacy.service.CategoryService;
import com.search.pharmacy.ws.model.CategoryDTO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getCategories() {
    log.debug("[ENDPOINT] request to get all categories");
    return ResponseEntity.ok(categoryService.getCategories());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategory(@PathVariable(value = "id") Long id) {
    log.debug("[ENDPOINT] request to get Category with id {}", id);
    return ResponseEntity.ok(categoryService.getCategory(id));
  }

  @PostMapping
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
    log.debug("[ENDPOINT] request to create a category : {}", categoryDTO.getName());
    return ResponseEntity.ok(categoryService.create(categoryDTO));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CategoryDTO> updateCategory(
      @PathVariable Long id, @RequestBody(required = false) Map<String, Object> fields) {
    log.info("[ENDPOINT] Received request to update category with id {}", id);
    return ResponseEntity.ok(categoryService.update(id, fields));
  }
}
