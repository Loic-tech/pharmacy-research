package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.User;
import com.search.pharmacy.ws.model.CompleteUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompletUserMapper extends AbstractMapper<User, CompleteUserDTO> {}