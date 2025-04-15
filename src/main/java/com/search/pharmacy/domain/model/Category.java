package com.search.pharmacy.domain.model;

import com.search.pharmacy.common.exception.orm.AbstractEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_category"))
public class Category extends AbstractEntity<Long> {

    @Column(name = "name")
    @ToString.Include
    private String name;
}
