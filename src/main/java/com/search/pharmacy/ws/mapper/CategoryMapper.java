package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Category;
import com.search.pharmacy.ws.model.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends AbstractMapper<Category, CategoryDTO> {}
