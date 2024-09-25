package com.search.pharmacy.service;

import com.search.pharmacy.repository.MedicineRepository;
import com.search.pharmacy.ws.mapper.MedicineMapper;
import com.search.pharmacy.ws.model.MedicineDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.of;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicineService {

  private final MedicineRepository medicineRepository;
  private final MedicineMapper mapper;

  @Transactional
  public MedicineDTO create(MedicineDTO medicineDTO) {
    MedicineDTO newMedicineDTO =
        of(medicineDTO)
            .map(mapper::toEntity)
            .map(medicineRepository::save)
            .map(mapper::toDto)
            .orElseThrow();
    return newMedicineDTO;
  }

  public List<MedicineDTO> getMedicines() {
    return medicineRepository.findAll().stream().map(mapper::toDto).toList();
  }
}
