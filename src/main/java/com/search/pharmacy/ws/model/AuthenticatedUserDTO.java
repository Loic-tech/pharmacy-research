package com.search.pharmacy.ws.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthenticatedUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private Set<RoleDTO> roles;
}
