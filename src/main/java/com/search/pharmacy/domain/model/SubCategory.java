package com.search.pharmacy.domain.model;

import com.search.pharmacy.common.exception.orm.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "SUB_CATEGORIES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "ID_SUB_CATEGORY"))
public class SubCategory extends AbstractEntity<Long> {

  @Column(name = "name")
  @ToString.Include
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_category", nullable = false)
  @ToString.Include
  private Category category;
}
