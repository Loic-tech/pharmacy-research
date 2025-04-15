package com.search.pharmacy.ws.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ApiModel(
    value = "FileDTO",
    description = "Used when generating a pre-signed Url OR Creating files")
public class FileDTO {

  @ApiModelProperty(value = "Url File")
  @NotEmpty(
      message = "url cannot be null")
  private String url;
}
