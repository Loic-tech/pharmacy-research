package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.ws.model.MedicineDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicineDetailMapper extends AbstractMapper<Medicine, MedicineDetailDTO> {

    @Override
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "smallDescription", source = "entity.smallDescription")
    @Mapping(target = "reference", source = "entity.reference")
    @Mapping(target = "quantity", source = "entity.quantity")
    @Mapping(target = "completeDescription", source = "entity.completeDescription")
    @Mapping(target = "usingAdvice", source = "entity.usingAdvice")
    @Mapping(target = "composition", source = "entity.composition")
    @Mapping(target = "newPrice", source = "entity.newPrice")
    @Mapping(target = "url", source = "entity.url")
    @Mapping(target = "oldPrice", source = "entity.oldPrice")
    @Mapping(target = "categoryDTO.id", source = "entity.category.id")
    @Mapping(target = "categoryDTO.name", source = "entity.category.name")
    @Mapping(target = "subCategoryDTO.id", source = "entity.subCategory.id")
    @Mapping(target = "subCategoryDTO.name", source = "entity.subCategory.name")
    @Mapping(target = "subCategoryDTO.categoryDTO.id", source = "entity.subCategory.id")
    @Mapping(target = "subCategoryDTO.categoryDTO.name", source = "entity.subCategory.name")
    MedicineDetailDTO toDTO(final Medicine entity);
}
