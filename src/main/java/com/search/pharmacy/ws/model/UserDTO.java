package com.search.pharmacy.ws.model;

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
  private String password;
  private String birthDate;
  private List<String> urls;
  private boolean isValid;
  private List<RoleDTO> roles;
}
