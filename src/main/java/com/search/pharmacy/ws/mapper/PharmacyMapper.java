package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Pharmacy;
import com.search.pharmacy.ws.model.PharmacyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PharmacyMapper extends AbstractMapper<Pharmacy, PharmacyDTO> {

    @Override
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "address", source = "entity.address")
    @Mapping(target = "contact", source = "entity.contact")
    @Mapping(target = "latitude", source = "entity.latitude")
    @Mapping(target = "longitude", source = "entity.longitude")
    PharmacyDTO toDTO(Pharmacy entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "contact", source = "contact")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    Pharmacy toEntity(PharmacyDTO dto);
}
