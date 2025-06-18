package com.search.pharmacy.ws.controller;

import com.search.pharmacy.service.SubCategoryService;
import com.search.pharmacy.ws.model.SubCategoryDTO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subcategories")
@RequiredArgsConstructor
@Slf4j
public class SubCategoryController {

  private final SubCategoryService subCategoryService;

  @GetMapping
  public ResponseEntity<List<SubCategoryDTO>> getSubCategories() {
    log.debug("[ENDPOINT] request to get all sub categories");
    return ResponseEntity.ok(subCategoryService.getSubCategories());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SubCategoryDTO> getSubCategory(@PathVariable(value = "id") Long id) {
    log.debug("[ENDPOINT] request to get sub Category with id {}", id);
    return ResponseEntity.ok(subCategoryService.getSubCategory(id));
  }

  @PostMapping
  public ResponseEntity<SubCategoryDTO> createSubCategory(
      @RequestBody SubCategoryDTO subCategoryDTO) {
    log.debug("[ENDPOINT] request to create a sub category : {}", subCategoryDTO.getName());
    return ResponseEntity.ok(subCategoryService.create(subCategoryDTO));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<SubCategoryDTO> updateCategory(
      @PathVariable Long id, @RequestBody(required = false) Map<String, Object> fields) {
    log.info("[ENDPOINT] Received request to update a sub category with id {}", id);
    return ResponseEntity.ok(subCategoryService.update(id, fields));
  }
}
