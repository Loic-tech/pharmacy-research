package com.search.pharmacy.ws.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.search.pharmacy.service.MedicineService;
import com.search.pharmacy.ws.model.MedicineDTO;
import com.search.pharmacy.ws.model.MedicineDetailDTO;
import com.search.pharmacy.ws.model.MedicineListDTO;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
      @Valid @ModelAttribute MedicineDTO medicineDTO, @RequestPart MultipartFile file)
      throws Exception {
    log.debug("[ENDPOINT] request to create a medicine : {}", medicineDTO.getName());
    return ResponseEntity.ok(medicineService.create(medicineDTO, file));
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<MedicineDetailDTO> getMedicine(@PathVariable(value = "id") Long id) {
    log.debug("[ENDPOINT] request to get Medicine : {}", id);
    return ResponseEntity.ok(medicineService.getMedicine(id));
  }

  /**
   * Endpoint unifié pour rechercher des médicaments Supporte la recherche par nom, catégorie, ou
   * les deux
   *
   * @param name Nom du médicament (optionnel)
   * @param categoryId ID de la catégorie (optionnel)
   * @param page Numéro de page (défaut: 0)
   * @param size Taille de page (défaut: 10)
   * @return Page de médicaments correspondant aux critères
   */
  @GetMapping
  public ResponseEntity<Page<MedicineListDTO>> getMedicinesBySearchCriteria(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Long categoryId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    try {
      Page<MedicineListDTO> medicines =
          medicineService.searchMedicines(name, categoryId, page, size);
      return ResponseEntity.ok(medicines);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<MedicineDetailDTO> updateMedicine(
      @PathVariable(value = "id") Long id,
      @RequestBody(required = false) Map<String, Object> fields,
      @RequestPart(required = false) MultipartFile file) {
    log.info("[ENDPOINT] Received request to update medicine with id {}", id);
    return ResponseEntity.ok(medicineService.update(id, fields, file));
  }

  @DeleteMapping(value = "/file/{medicineId}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Void> deleteFile(@PathVariable(value = "medicineId") Long medicineId) {
    log.info("[ENDPOINT] Received request to delete file with id {}", medicineId);
    medicineService.deleteFile(medicineId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Void> deleteMedicine(@PathVariable(value = "id") Long medicineId) {
    log.info("[ENDPOINT] Received request to delete medicine with id {}", medicineId);

    medicineService.delete(medicineId);

    return ResponseEntity.ok().build();
  }
}
