package com.search.pharmacy.ws.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

  private Long id;
  @NotNull(message = "firstName cannot be null")
  private String firstName;
  @NotNull(message = "lastName cannot be null")
  private String lastName;
  @NotNull(message = "email cannot be null")
  @Email(message = "email is not valid")
  private String email;
  @NotNull(message = "password cannot be null")
  private String password;
  private List<RoleDTO> roles;
}
