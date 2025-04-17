package com.search.pharmacy.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Class responsible for home page data */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicineListDTO {

  private Long id;
  private String name;
  private String url;
  private Double newPrice;
}
