package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.User;
import com.search.pharmacy.ws.model.PartialUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartialUserMapper extends AbstractMapper<User, PartialUserDTO> {}
