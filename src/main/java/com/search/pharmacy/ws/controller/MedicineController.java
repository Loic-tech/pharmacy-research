package com.search.pharmacy.ws.controller;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.ALREADY_REPORTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.search.pharmacy.common.exception.NotFoundException;
import com.search.pharmacy.service.MedicineService;
import com.search.pharmacy.utils.Utils;
import com.search.pharmacy.ws.model.MedicineDTO;
import com.search.pharmacy.ws.model.MedicineDetailDTO;
import com.search.pharmacy.ws.model.MedicineListDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

  private static final Logger log = LoggerFactory.getLogger(MedicineController.class);
  private final MedicineService medicineService;
  private final ObjectMapper objectMapper;

  @PostMapping
  public ResponseEntity<MedicineDTO> createMedicine(@ModelAttribute MedicineDTO medicineDTO, @RequestPart MultipartFile file) throws Exception {
    return ResponseEntity.ok(medicineService.create(medicineDTO, file));
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<MedicineListDTO>> getMedicines() {
    return ResponseEntity.ok(medicineService.getMedicines());
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<MedicineDetailDTO> getMedicine(@PathVariable(value = "id") Long id) {
    log.debug("[ENDPOINT] request to get Medicine : {}", id);
    return ResponseEntity.ok(medicineService.getMedicine(id));
  }

  @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<MedicineDetailDTO> updateMedicine(
          @PathVariable(value = "id") Long id, @RequestParam(required = false) Map<String, Object> fields, @RequestPart(required = false) MultipartFile file) {
    log.info("[ENDPOINT] Received request to update medicine with id {}", id);
    return ResponseEntity.ok(medicineService.update(id, fields, file));
  }

  @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> deleteMedicine(@PathVariable(value = "id") Long medicineId) {
    log.info("[ENDPOINT] Received request to delete medicine with id {}", medicineId);

    medicineService.delete(medicineId);

    return ResponseEntity.ok().build();
  }
}
