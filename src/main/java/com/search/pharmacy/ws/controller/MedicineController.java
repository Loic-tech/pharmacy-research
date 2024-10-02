package com.search.pharmacy.ws.controller;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.ALREADY_REPORTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.search.pharmacy.common.exception.InvalidParamException;
import com.search.pharmacy.common.exception.NotFoundException;
import com.search.pharmacy.service.MedicineService;
import com.search.pharmacy.utils.Utils;
import com.search.pharmacy.ws.model.MedicineDTO;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

  private static final Logger log = LoggerFactory.getLogger(MedicineController.class);
  private final MedicineService medicineService;
  private final ObjectMapper objectMapper;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<MedicineDTO> createMedicine(@RequestBody MedicineDTO medicineDTO) {
    return ResponseEntity.ok(medicineService.create(medicineDTO));
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<MedicineDTO>> getMedicines() {
    return ResponseEntity.ok(medicineService.getMedicines());
  }

  @PatchMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<MedicineDTO> updateMedicine(
      @PathVariable(value = "id") Long id, @RequestBody JsonPatch jsonPatch)
      throws JsonPatchException {
    log.info("[ENDPOINT] Received request to update medicine with id {}", id);

    MedicineDTO medicineById =
        ofNullable(medicineService.getMedicine(id))
            .orElseThrow(
                () -> {
                  log.warn("[ENDPOINT] Medicine with id {} not found", id);
                  return new NotFoundException(String.format("Medicine with id %s not found", id));
                });

    JsonNode origin = objectMapper.convertValue(medicineById, JsonNode.class);
    JsonNode patched;
    try {
      patched = jsonPatch.apply(origin);
    } catch (JsonPatchException e) {
      if (Utils.ERROR_MESSAGE_JSON.equals(e.getMessage())) {
        return ResponseEntity.status(ALREADY_REPORTED).build();
      } else {
        log.error("Cannot apply patch to medicine: {}", id);
        throw e;
      }
    }

    MedicineDTO patchedMedicineDTO = objectMapper.convertValue(patched, MedicineDTO.class);

    return ResponseEntity.ok(medicineService.update(id, patchedMedicineDTO));
  }
}
