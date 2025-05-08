package com.search.pharmacy.service;

import com.search.pharmacy.repository.RoleRepository;
import com.search.pharmacy.ws.mapper.RoleMapper;
import com.search.pharmacy.ws.model.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

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

    public List<RoleDTO> getRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toDTO).toList();
    }
}
