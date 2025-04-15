package com.search.pharmacy.common.exception.mapper;

import com.search.pharmacy.domain.model.Category;
import com.search.pharmacy.domain.model.SubCategory;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Named("AssetQualifier")
public class AssetQualifier {

    @Named("IdToCategory")
    public Category idToCategory(Long id) {
       Category category = null;
       if (!Objects.isNull(id)) {
           category = new Category();
           category.setId(id);
       }
       return category;
    }

    @Named("IdToSubCategory")
    public SubCategory idToSubCategory(Long id) {
        SubCategory subCategory = null;
        if (!Objects.isNull(id)) {
            subCategory = new SubCategory();
            subCategory.setId(id);
        }
        return subCategory;
    }
}
