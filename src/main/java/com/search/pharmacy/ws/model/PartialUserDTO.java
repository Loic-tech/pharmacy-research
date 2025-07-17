package com.search.pharmacy.ws.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PartialUserDTO {

    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "password cannot be null")
    private String password;
    private boolean isValid;
}
