package com.search.pharmacy.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.search.pharmacy.common.exception.orm.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_line")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_order_line"))
@Builder
public class OrderLine extends AbstractEntity<Long> {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_medicine", nullable = false)
  @ToString.Include
  private Medicine medicine;

  @Column(name = "quantity")
  private Integer quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  private Order order;
}
