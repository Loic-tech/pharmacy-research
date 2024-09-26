package com.search.pharmacy.ws.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.search.pharmacy.service.MedicineService;
import com.search.pharmacy.ws.model.MedicineDTO;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicineDTO> createMedicine(@RequestBody MedicineDTO medicineDTO) {
        return ResponseEntity.ok(medicineService.create(medicineDTO));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineDTO>> getMedicines() {
        return ResponseEntity.ok(medicineService.getMedicines());
    }
}
