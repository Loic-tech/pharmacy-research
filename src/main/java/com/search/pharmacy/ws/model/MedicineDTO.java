package com.search.pharmacy.ws.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {
  private Long id;

  @NotNull(message = "name cannot be null")
  private String name;

  @NotNull(message = "small description cannot be null")
  private String smallDescription;

  private String url;

  @NotNull(message = "reference for the drug should not be null")
  private String reference;

  @NotNull(message = "quantity for the drug should not be null")
  private Integer quantity;

  private Double newPrice;

  private Double oldPrice;

  @NotNull(message = "complete description for the drug should not be null")
  private String completeDescription;

  @NotNull(message = "using advice description for the drug should not be null")
  private String usingAdvice;

  @NotNull(message = "composition for the drug should not be null")
  private String composition;

  @NotNull(message = "category description for the drug should not be null")
  private Long idCategory;

  private Long idSubCategory;
}
