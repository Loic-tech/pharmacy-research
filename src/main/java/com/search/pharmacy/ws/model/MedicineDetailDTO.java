package com.search.pharmacy.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicineDetailDTO {
  private Long id;

  private String name;

  private String smallDescription;

  private String url;

  private String reference;

  private Integer quantity;

  private Double newPrice;

  private Double oldPrice;

  private String completeDescription;

  private String usingAdvice;

  private String composition;

  private CategoryDTO categoryDTO;

  private SubCategoryDTO subCategoryDTO;
}
