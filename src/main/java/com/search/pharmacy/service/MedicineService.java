package com.search.pharmacy.service;

import com.search.pharmacy.common.exception.InvalidParamException;
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
    log.info("[MedicineService] Create Medicine : {}", medicineDTO);
    return of(medicineDTO)
        .map(mapper::toEntity)
        .map(medicineRepository::save)
        .map(mapper::toDTO)
        .orElseThrow(
            () -> {
              log.debug("Could not create a new medicine");
              return new InvalidParamException("Could not create a new medicine");
            });
  }

  public List<MedicineDTO> getMedicines() {
    return medicineRepository.findAll().stream().map(mapper::toDTO).toList();
  }

  public MedicineDTO getMedicine(Long medicineId) {
    return medicineRepository
        .findById(medicineId)
        .map(mapper::toDTO)
        .orElseThrow(
            () -> new InvalidParamException("Could not find medicine with id: " + medicineId));
  }

  @Transactional
  public MedicineDTO update(Long id, MedicineDTO medicineDTO) {
    return medicineRepository
        .findById(id)
        .map(medicine -> mapper.updateFromDTO(medicineDTO, medicine))
        .map(medicineRepository::save)
        .map(mapper::toDTO)
        .orElseThrow(
            () -> {
              log.debug("Could not update medicine");
              return new InvalidParamException("Could not update medicine");
            });
  }
}
