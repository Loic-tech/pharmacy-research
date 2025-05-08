package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Roles;
import com.search.pharmacy.domain.model.User;
import com.search.pharmacy.ws.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {Roles.class})
public interface UserMapper extends AbstractMapper<User, UserDTO> {

  @Override
  UserDTO toDTO(final User entity);

  @Override
  User toEntity(final UserDTO dto);
}
