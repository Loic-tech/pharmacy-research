package com.search.pharmacy.ws.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Data
public class StockDTO {

    private Long id;
    private Long idMedicine;
    private Long idPharmacy;
    private Integer quantity;
}
