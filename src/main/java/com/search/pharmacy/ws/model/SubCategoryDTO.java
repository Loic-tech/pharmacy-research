package com.search.pharmacy.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryDTO {
    private Long id;
    private String name;
    private CategoryDTO categoryDTO;
}
