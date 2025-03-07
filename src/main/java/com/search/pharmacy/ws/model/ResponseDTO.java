package com.search.pharmacy.ws.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String drugName;
    private String drugDescription;
    private String address;
    private Integer quantity;
    private Integer distance;
}
