package com.search.pharmacy.domain.model;

import com.search.pharmacy.common.exception.orm.AbstractEntity;
import jakarta.persistence.*;
import java.util.Set;
import lombok.*;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "MEDICINE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_medicine"))
public class Medicine extends AbstractEntity<Long> {

  @Column(name = "name")
  @ToString.Include
  private String name;

  @Column(name = "small_description")
  @ToString.Include
  private String smallDescription;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.ALL})
  @JoinTable(
      name = "MEDICINE_FILE",
      joinColumns = @JoinColumn(name = "ID_MEDICINE"),
      inverseJoinColumns = @JoinColumn(name = "ID_FILE"))
  @OrderBy(value = "id")
  @Accessors(chain = true)
  private Set<File> files;

  @Column(name = "reference")
  private String reference;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "complete_description")
  @ToString.Include
  private String completeDescription;

  @Column(name = "using_advice")
  @ToString.Include
  private String usingAdvice;

  @Column(name = "composition")
  @ToString.Include
  private String composition;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_category", nullable = false)
  @ToString.Include
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID_SUB_CATEGORY", nullable = false)
  @EqualsAndHashCode.Include
  @ToString.Include
  private SubCategory subCategory;
}
