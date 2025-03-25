package com.search.pharmacy.ws.controller;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.ALREADY_REPORTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.search.pharmacy.common.exception.NotFoundException;
import com.search.pharmacy.service.PharmacyService;
import com.search.pharmacy.utils.Utils;
import com.search.pharmacy.ws.model.PharmacyDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pharmacies")
@RequiredArgsConstructor
@Slf4j
public class PharmacyController {

  private final PharmacyService pharmacyService;
  private final ObjectMapper objectMapper;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<PharmacyDTO> createPharmacy(@RequestBody PharmacyDTO pharmacyDTO) {
    return new ResponseEntity<>(pharmacyService.createPharmacy(pharmacyDTO), HttpStatus.CREATED);
  }

  @PostMapping("/populate")
  public ResponseEntity<Void> populateDB() {
    pharmacyService.populateDB();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {
    return ResponseEntity.ok(pharmacyService.getPharmacies());
  }

  @GetMapping("{id}")
  public ResponseEntity<PharmacyDTO> getPharmacyById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(pharmacyService.getPharmacy(id));
  }

  @PatchMapping(value = "{id}", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<PharmacyDTO> updatePharmacy(
      @PathVariable("id") Long id, @RequestBody JsonPatch jsonPatch) throws JsonPatchException {
    PharmacyDTO pharmacyById =
        ofNullable(pharmacyService.getPharmacy(id))
            .orElseThrow(
                () -> {
                  log.warn("[ENDPOINT] Pharmacy with id {} not found", id);
                  return new NotFoundException(String.format("Pharmacy with id %s not found", id));
                });

    JsonNode origin = objectMapper.convertValue(pharmacyById, JsonNode.class);
    JsonNode patched;
    try {
      patched = jsonPatch.apply(origin);
    } catch (JsonPatchException e) {
      if (Utils.ERROR_MESSAGE_JSON.equals(e.getMessage())) {
        return ResponseEntity.status(ALREADY_REPORTED).build();
      } else {
        log.error("Cannot apply patch to pharmacy: {}", id);
        throw e;
      }
    }

    PharmacyDTO patchedPharmacyDTO = objectMapper.convertValue(patched, PharmacyDTO.class);

    return ResponseEntity.ok(pharmacyService.updatePharmacy(id, patchedPharmacyDTO));
  }

  @DeleteMapping(value = "{id}")
  public ResponseEntity<Void> deletePharmacy(@PathVariable("id") Long id) {
    pharmacyService.deletePharmacy(id);
     return ResponseEntity.ok().build();
  }
}
