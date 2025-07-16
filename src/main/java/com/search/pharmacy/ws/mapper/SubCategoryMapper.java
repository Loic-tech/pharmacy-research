package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.mapper.AssetQualifier;
import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.SubCategory;
import com.search.pharmacy.ws.model.SubCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {AssetQualifier.class})
public interface SubCategoryMapper extends AbstractMapper<SubCategory, SubCategoryDTO> {
  @Override
  @Mapping(target = "idCategory", source = "entity.category.id")
  SubCategoryDTO toDTO(final SubCategory entity);

  @Override
  @Mapping(
          target = "category",
          source = "idCategory",
          qualifiedByName = {"AssetQualifier", "IdToCategory"})
  SubCategory toEntity(final SubCategoryDTO dto);
}
