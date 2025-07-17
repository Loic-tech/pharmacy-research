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
  private String firstName;
  private String lastName;
  @Email(message = "Email should be valid")
  private String email;
  @NotNull(message = "password cannot be null")
  private String password;
  private String birthDate;
  private List<String> urls;
  private boolean isValid;
  private List<RoleDTO> roles;
}
