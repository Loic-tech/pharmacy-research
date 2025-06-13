package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.ws.model.MedicineListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicineListMapper extends AbstractMapper<Medicine, MedicineListDTO> {

    @Override
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "url", source = "entity.url")
    @Mapping(target = "newPrice", source = "entity.newPrice")
    @Mapping(target = "oldPrice", source = "entity.oldPrice")
    MedicineListDTO toListDTO(final Medicine entity);
}
