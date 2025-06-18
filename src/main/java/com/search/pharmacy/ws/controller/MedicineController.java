package com.search.pharmacy.ws.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.search.pharmacy.service.MedicineService;
import com.search.pharmacy.ws.model.MedicineDTO;
import com.search.pharmacy.ws.model.MedicineDetailDTO;
import com.search.pharmacy.ws.model.MedicineListDTO;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

  private static final Logger log = LoggerFactory.getLogger(MedicineController.class);
  private final MedicineService medicineService;

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<MedicineDTO> createMedicine(
      @ModelAttribute MedicineDTO medicineDTO, @RequestPart MultipartFile file) throws Exception {
    log.debug("[ENDPOINT] request to create a medicine : {}", medicineDTO.getName());
    return ResponseEntity.ok(medicineService.create(medicineDTO, file));
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<MedicineListDTO>> getMedicines(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    log.debug("[ENDPOINT] request to get all medicines :");
    return ResponseEntity.ok(medicineService.getMedicines(page, size));
  }

  @GetMapping(value = "/by-category", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<MedicineListDTO>> getMedicinesByCategory(
      @RequestParam Long categoryId,
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    log.debug("[ENDPOINT] request to get all medicines by category : {}", categoryId);
    return ResponseEntity.ok(medicineService.getMedicinesByCategory(categoryId, page, size));
  }

  @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<MedicineListDTO>> filterMedicines(
      @RequestParam String name,
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    log.debug("[ENDPOINT] request to filter medicines by name : {}", name);
    return ResponseEntity.ok(medicineService.filterMedicines(name, page, size));
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<MedicineDetailDTO> getMedicine(@PathVariable(value = "id") Long id) {
    log.debug("[ENDPOINT] request to get Medicine : {}", id);
    return ResponseEntity.ok(medicineService.getMedicine(id));
  }

  @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<MedicineDetailDTO> updateMedicine(
          @PathVariable(value = "id") Long id,
          @RequestParam(required = false) Map<String, Object> fields,
          @RequestPart(required = false) MultipartFile file) {
    log.info("[ENDPOINT] Received request to update medicine with id {}", id);
    return ResponseEntity.ok(medicineService.update(id, fields, file));
  }

  @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Void> deleteMedicine(@PathVariable(value = "id") Long medicineId) {
    log.info("[ENDPOINT] Received request to delete medicine with id {}", medicineId);

    medicineService.delete(medicineId);

    return ResponseEntity.ok().build();
  }
}
