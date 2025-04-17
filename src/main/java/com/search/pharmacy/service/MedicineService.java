package com.search.pharmacy.service;

import static java.util.Optional.ofNullable;

import com.search.pharmacy.common.exception.InvalidParamException;
import com.search.pharmacy.repository.MedicineRepository;
import com.search.pharmacy.ws.mapper.MedicineDetailMapper;
import com.search.pharmacy.ws.mapper.MedicineListMapper;
import com.search.pharmacy.ws.mapper.MedicineMapper;
import com.search.pharmacy.ws.model.MedicineDTO;
import java.util.*;

import com.search.pharmacy.ws.model.MedicineDetailDTO;
import com.search.pharmacy.ws.model.MedicineListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicineService {

  private final MedicineRepository medicineRepository;
  private final MedicineMapper mapper;
  private final MinioService minioService;
  private final MedicineListMapper medicineListMapper;
  private final MedicineDetailMapper medicineDetailMapper;

  @Transactional
  public MedicineDTO create(MedicineDTO medicineDTO, MultipartFile file) throws Exception {
    log.info("[MedicineService] Create Medicine : {}", medicineDTO);

    String url = minioService.uploadFiles(file);
    medicineDTO.setUrl(url);

    return Optional.of(medicineDTO)
        .map(mapper::toEntity)
        .map(medicineRepository::save)
        .map(mapper::toDTO)
        .orElseThrow(
            () -> {
              log.debug("Could not create a new medicine");
              return new InvalidParamException("Could not create a new medicine");
            });
  }

    public List<MedicineListDTO> getMedicines() {
    return medicineRepository.findAll().stream().map(medicineListMapper::toListDTO).toList();
  }

  public MedicineDetailDTO getMedicine(Long medicineId) {
    return medicineRepository
        .findById(medicineId)
        .map(medicineDetailMapper::toDTO)
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

  public void delete(Long medicineId) {
    medicineRepository
        .findById(medicineId)
        .ifPresentOrElse(
            medicineRepository::delete,
            () -> log.debug("Medicine with id {} not found", medicineId));
  }

  private String getPresignedUrl(String filename) {
    return "http://localhost:9001/choupi/" + filename;
  }
}
