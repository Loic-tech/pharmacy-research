package com.search.pharmacy.ws.controller;

import com.search.pharmacy.service.PharmacyService;
import com.search.pharmacy.ws.model.PharmacyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PharmacyDTO> createPharmacy(@RequestBody PharmacyDTO pharmacyDTO) {
        return new ResponseEntity<>(pharmacyService.createPharmacy(pharmacyDTO), HttpStatus.CREATED);
    }

    @GetMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {
        return ResponseEntity.ok(pharmacyService.getPharmacies());
    }
}
