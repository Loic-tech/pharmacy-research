package com.search.pharmacy.service;

import com.search.pharmacy.common.exception.InvalidParamException;
import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.repository.MedicineRepository;
import com.search.pharmacy.ws.mapper.MedicineDetailMapper;
import com.search.pharmacy.ws.mapper.MedicineListMapper;
import com.search.pharmacy.ws.mapper.MedicineMapper;
import com.search.pharmacy.ws.model.MedicineDTO;
import com.search.pharmacy.ws.model.MedicineDetailDTO;
import com.search.pharmacy.ws.model.MedicineListDTO;
import jakarta.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
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

  public Page<MedicineListDTO> searchMedicines(
          String name,
          Long categoryId,
          int page,
          int size
  ) {
    Pageable pageable = Pageable.ofSize(size).withPage(page);

    Page<Medicine> medicinesPage;

    if (name != null && !name.trim().isEmpty() && categoryId != null) {
      medicinesPage = medicineRepository
              .findAllByNameIgnoreCaseContainingAndCategory_Id(name.trim(), categoryId, pageable);
    } else if (name != null && !name.trim().isEmpty()) {
      medicinesPage = medicineRepository
              .findAllByNameIgnoreCaseContaining(name.trim(), pageable);
    } else if (categoryId != null) {
      medicinesPage = medicineRepository
              .findAllByCategory_Id(categoryId, pageable);
    } else {
      medicinesPage = medicineRepository.findAll(pageable);
    }

    return medicinesPage.map(medicineListMapper::toListDTO);
  }

  public MedicineDetailDTO getMedicine(Long medicineId) {
    return medicineRepository
        .findById(medicineId)
        .map(medicineDetailMapper::toDTO)
        .orElseThrow(
            () -> new InvalidParamException("Could not find medicine with id: " + medicineId));
  }

  @Transactional
  public MedicineDetailDTO update(Long id, Map<String, Object> fields, MultipartFile file) {
    Optional<Medicine> optionalMedicine = medicineRepository.findById(id);

    if (optionalMedicine.isPresent()) {
      Medicine patchMedicine = optionalMedicine.get();

      fields.forEach(
          (key, value) -> {
            Field field = ReflectionUtils.findField(Medicine.class, key);
            if (field != null) {
              field.setAccessible(true);
              ReflectionUtils.setField(field, patchMedicine, value);
            }
          });

      if (file != null) {
        String fileName = extractFilename(patchMedicine.getUrl());
        try {
          minioService.deleteFile(fileName);
        } catch (Exception e) {
          throw new RuntimeException(
              "Erreur lors de la suppression du fichier: " + e.getMessage(), e);
        }

        try {
          String newUrl = minioService.uploadFiles(file);
          patchMedicine.setUrl(newUrl);
        } catch (Exception e) {
          throw new RuntimeException(
              "Erreur lors du téléchargement du fichier: " + e.getMessage(), e);
        }
      }

      Medicine savedMedicine = medicineRepository.save(patchMedicine);

      return medicineDetailMapper.toDTO(savedMedicine);
    } else {
      throw new EntityNotFoundException("Médicament avec l'ID " + id + " non trouvé");
    }
  }

  public void delete(Long medicineId) {
    log.info("[MedicineService] Delete Medicine with id {}", medicineId);
    Optional<Medicine> optionalMedicine = medicineRepository.findById(medicineId);
    if (optionalMedicine.isPresent()) {
      Medicine medicine = optionalMedicine.get();
      String fileName = extractFilename(medicine.getUrl());
      try {
        minioService.deleteFile(fileName);
      } catch (Exception e) {
        log.error("Erreur lors de la suppression du fichier: {}", e.getMessage(), e);
      }
      medicineRepository
          .findById(medicineId)
          .ifPresentOrElse(
              medicineRepository::delete,
              () -> log.debug("Medicine with id {} not found", medicineId));
    }
  }

  public void deleteFile(Long medicineId) {
    log.info("[MedicineService] Delete Medicine File with id {}", medicineId);
    Optional<Medicine> optionalMedicine = medicineRepository.findById(medicineId);
    if (optionalMedicine.isPresent()) {
      Medicine medicine = optionalMedicine.get();
      String fileName = extractFilename(medicine.getUrl());
      try {
        minioService.deleteFile(fileName);
        medicine.setUrl(null);
      } catch (Exception e) {
        log.error("Erreur lors de la suppression du fichier: {}", e.getMessage(), e);
      }
    }
  }

  public String extractFilename(String url) {
    int lastSlashIndex = url.lastIndexOf('/');
    if (lastSlashIndex != -1 && lastSlashIndex < url.length() - 1) {
      return url.substring(lastSlashIndex + 1);
    }
    return url;
  }
}
