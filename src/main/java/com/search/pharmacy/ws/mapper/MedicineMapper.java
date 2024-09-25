package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.ws.model.MedicineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicineMapper {

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "description", source = "entity.description")
    MedicineDTO toDto(Medicine entity);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    Medicine toEntity(MedicineDTO dto);
}
