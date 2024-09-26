package com.search.pharmacy.ws.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyDTO {
    private Long id;
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "address cannot be null")
    private String address;
    @NotNull(message = "contact cannot be null")
    private String contact;
    @NotNull(message = "latitude cannot be null")
    private Double latitude;
    @NotNull(message = "longitude cannot be null")
    private Double longitude;
}
