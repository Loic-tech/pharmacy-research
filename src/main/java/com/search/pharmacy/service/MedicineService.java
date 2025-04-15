package com.search.pharmacy.service;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import com.search.pharmacy.common.exception.InvalidParamException;
import com.search.pharmacy.repository.MedicineRepository;
import com.search.pharmacy.ws.mapper.MedicineMapper;
import com.search.pharmacy.ws.model.FileDTO;
import com.search.pharmacy.ws.model.MedicineDTO;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicineService {

  private final MedicineRepository medicineRepository;
  private final MedicineMapper mapper;
  private final MinioService minioService;

  @Transactional
  public MedicineDTO create(MedicineDTO medicineDTO) {
    log.info("[MedicineService] Create Medicine : {}", medicineDTO);

    List<FileDTO> fileDTOS = new ArrayList<>();

    ofNullable(medicineDTO.getMultipartFiles()).ifPresent(files -> files.forEach(file -> {
        try {
            minioService.uploadFiles(file);
            fileDTOS.add(new FileDTO(minioService.generatePresignedUrl(file.getOriginalFilename())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }));

    medicineDTO.setFiles(fileDTOS);

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
