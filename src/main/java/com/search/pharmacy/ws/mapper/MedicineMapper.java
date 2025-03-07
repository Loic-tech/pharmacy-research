package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.ws.model.MedicineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedicineMapper extends AbstractMapper<Medicine, MedicineDTO> {

    @Override
    MedicineDTO toDTO(final Medicine entity);

    @Override
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    Medicine toEntity(MedicineDTO dto);

    @Mapping(target = "id", ignore = true)
    Medicine updateFromDTO(final MedicineDTO dto, @MappingTarget Medicine entity);
}
