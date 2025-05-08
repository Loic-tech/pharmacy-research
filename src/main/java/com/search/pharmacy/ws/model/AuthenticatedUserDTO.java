package com.search.pharmacy.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthenticatedUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private List<RoleDTO> roles;
}
