package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.mapper.AssetQualifier;
import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.ws.model.MedicineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {AssetQualifier.class})
public interface MedicineMapper extends AbstractMapper<Medicine, MedicineDTO> {

  @Override
  MedicineDTO toDTO(final Medicine entity);

  @Override
  @Mapping(target = "id", source = "dto.id")
  @Mapping(target = "name", source = "dto.name")
  @Mapping(target = "smallDescription", source = "dto.smallDescription")
  @Mapping(target = "reference", source = "dto.reference")
  @Mapping(target = "quantity", source = "dto.quantity")
  @Mapping(target = "completeDescription", source = "dto.completeDescription")
  @Mapping(target = "usingAdvice", source = "dto.usingAdvice")
  @Mapping(target = "composition", source = "dto.composition")
  @Mapping(
      target = "category",
      source = "idCategory",
      qualifiedByName = {"AssetQualifier", "IdToCategory"})
  @Mapping(
      target = "subCategory",
      source = "idSubCategory",
      qualifiedByName = {"AssetQualifier", "IdToSubCategory"})
  Medicine toEntity(MedicineDTO dto);



  @Mapping(target = "id", ignore = true)
  Medicine updateFromDTO(final MedicineDTO dto, @MappingTarget Medicine entity);
}
