package com.search.pharmacy.domain.model;

import com.search.pharmacy.common.exception.orm.AbstractEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_order"))
public class Order extends AbstractEntity<Long> {

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "order_medicine",
      joinColumns = @JoinColumn(name = "id_order"),
      inverseJoinColumns = @JoinColumn(name = "id_medicine"))
  private List<Medicine> medicines;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user", nullable = false)
  @ToString.Include
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OrderStatus status;

  @Column(name = "total_amount", nullable = false, insertable = false, updatable = false)
  private Double totalAmount;

  @Column(name = "address")
  private String address;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "comment")
  private String comment;

  @Column(name = "order_date")
  private LocalDateTime orderDate = LocalDateTime.now();

  public enum OrderStatus {
    EN_ATTENTE,
    CONFIRMEE,
    EN_PREPARATION,
    EXPEDIEE,
    LIVREE,
    ANNULEE,
    RETOURNEE,
    REMBOURSEE
  }
}
