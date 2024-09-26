package com.search.pharmacy.ws.model;

import lombok.*;
import jakarta.validation.constraints.NotNull;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {
    private Long id;
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "description cannot be null")
    private String description;
}
