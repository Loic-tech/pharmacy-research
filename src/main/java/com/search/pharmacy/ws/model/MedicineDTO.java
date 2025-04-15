package com.search.pharmacy.ws.model;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

  private List<MultipartFile> multipartFiles;

  @NotNull(message = "Images for the drug should not be null")
  @ApiModelProperty(value = "list of files associated to the current drug")
  private List<FileDTO> files;

  @NotNull(message = "reference for the drug should not be null")
  private String reference;

  @NotNull(message = "quantity for the drug should not be null")
  private Integer quantity;

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
