package com.search.pharmacy.service;

import com.search.pharmacy.common.exception.InvalidParamException;
import com.search.pharmacy.common.exception.NotFoundException;
import com.search.pharmacy.repository.MedicineRepository;
import com.search.pharmacy.repository.PharmacyRepository;
import com.search.pharmacy.ws.mapper.PharmacyMapper;
import com.search.pharmacy.ws.model.PharmacyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.of;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyService {

  private final PharmacyRepository pharmacyRepository;
  private final PharmacyMapper mapper;
  private final MedicineRepository medicineRepository;
  private final ExcelService excelService;

  @Transactional
  public PharmacyDTO createPharmacy(PharmacyDTO pharmacyDTO) {
    return of(pharmacyDTO)
        .map(mapper::toEntity)
        .map(pharmacyRepository::save)
        .map(mapper::toDTO)
        .orElseThrow(
            () -> {
              log.debug("Could not create a new pharmacy");
              return new InvalidParamException("Could not create a new pharmacy");
            });
  }

  @Transactional
  public void populateDB() {
    excelService.addPharmaciesListFromGouv();
  }

  public List<PharmacyDTO> getPharmacies() {
    return pharmacyRepository.findAll().stream().map(mapper::toDTO).toList();
  }

  public PharmacyDTO getPharmacy(Long id) {
    return pharmacyRepository
        .findById(id)
        .map(mapper::toDTO)
        .orElseThrow(
            () -> {
              log.debug("Could not find pharmacy");
              return new NotFoundException("Could not find pharmacy");
            });
  }

  @Transactional
  public PharmacyDTO updatePharmacy(Long id, PharmacyDTO pharmacyDTO) {
    return pharmacyRepository
        .findById(id)
        .map(pharmacy -> mapper.updateFromDTO(pharmacyDTO, pharmacy))
        .map(pharmacyRepository::save)
        .map(mapper::toDTO)
        .orElseThrow(
            () -> {
              log.debug("Could not update pharmacy");
              return new InvalidParamException("Could not update pharmacy");
            });
  }

  public void deletePharmacy(Long pharmacyId) {
      pharmacyRepository.findById(pharmacyId).ifPresentOrElse(
              pharmacyRepository::delete, () -> log.debug("Could not delete pharmacy")
      );
  }
}
