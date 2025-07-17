package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.Roles;
import com.search.pharmacy.repository.RoleRepository;
import com.search.pharmacy.ws.mapper.RoleMapper;
import com.search.pharmacy.ws.model.RoleDTO;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  public RoleDTO createRole(RoleDTO roleDTO) {
    return Optional.of(roleDTO)
        .map(roleMapper::toEntity)
        .map(roleRepository::save)
        .map(roleMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("Could not create a new role"));
  }

  public RoleDTO updateRole(Long id, Map<String, Object> fields) {
    Optional<Roles> optionalRole = roleRepository.findById(id);

    if (optionalRole.isPresent()) {
      fields.forEach(
          (key, value) -> {
            Field field = ReflectionUtils.findField(Roles.class, key);
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, optionalRole.get(), value);
          });
      return roleMapper.toDTO(roleRepository.save(optionalRole.get()));
    }
    return null;
  }

  public List<RoleDTO> getRoles() {
    return roleRepository.findAll().stream().map(roleMapper::toDTO).toList();
  }

  public void deleteRole(Long id) {
    roleRepository.deleteById(id);
  }
}
