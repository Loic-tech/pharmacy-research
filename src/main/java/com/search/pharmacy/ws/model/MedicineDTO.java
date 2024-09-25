package com.search.pharmacy.ws.model;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {
    private Long id;
    private String name;
    private String description;
}
