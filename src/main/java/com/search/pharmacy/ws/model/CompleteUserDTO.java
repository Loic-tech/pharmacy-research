package com.search.pharmacy.ws.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompleteUserDTO {

    @NotNull(message = "firstName cannot be null")
    private String firstName;
    @NotNull(message = "lastName cannot be null")
    private String lastName;
    @NotNull(message = "email cannot be null")
    private String birthDate;
    @NotNull(message = "urls cannot be null")
    private List<String> urls;
}
