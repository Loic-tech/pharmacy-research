package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.File;
import com.search.pharmacy.ws.model.FileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper extends AbstractMapper<File, FileDTO> {}
