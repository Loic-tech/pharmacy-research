package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.SubCategory;
import com.search.pharmacy.ws.model.SubCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper extends AbstractMapper<SubCategory, SubCategoryDTO> {
    @Override
    @Mapping(target = "categoryDTO.id", source = "entity.category.id")
    @Mapping(target = "categoryDTO.name", source = "entity.category.name")
    SubCategoryDTO toDTO(final SubCategory entity);

    @Override
    SubCategory toEntity(final SubCategoryDTO dto);
}
