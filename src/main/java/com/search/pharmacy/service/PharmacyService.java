package com.search.pharmacy.service;

import com.search.pharmacy.common.exception.InvalidParamException;
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

    @Transactional
    public PharmacyDTO createPharmacy(PharmacyDTO pharmacyDTO) {
        return of(pharmacyDTO).map(mapper::toEntity).map(pharmacyRepository::save).map(mapper::toDTO).orElseThrow(
                () -> {
                    log.debug("Could not create a new pharmacy");
                    return new InvalidParamException("Could not create a new pharmacy");
                }
        );
    }

    public List<PharmacyDTO> getPharmacies() {
        return pharmacyRepository.findAll().stream().map(mapper::toDTO).toList();
    }
}
