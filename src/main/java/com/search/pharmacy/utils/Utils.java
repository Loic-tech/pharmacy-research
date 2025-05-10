package com.search.pharmacy.utils;

import com.search.pharmacy.domain.model.Roles;
import com.search.pharmacy.ws.model.RoleDTO;

import java.util.Set;
import java.util.stream.Collectors;

public final class Utils {
    public static final String ERROR_MESSAGE_JSON = "no such path in target JSON document";

    public static Set<RoleDTO> convertToRoleDTOs(Set<Roles> roles) {
        return roles.stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .collect(Collectors.toSet());
    }
}
