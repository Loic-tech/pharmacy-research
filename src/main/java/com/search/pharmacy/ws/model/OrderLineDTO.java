package com.search.pharmacy.ws.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderLineDTO {

    private Long id;
    private Long medicineId;
    private Integer quantity;
}
