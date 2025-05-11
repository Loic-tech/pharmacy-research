package com.search.pharmacy.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_order")
  private Long id;

  @Column(name = "order_number")
  private String orderNumber;

  @OneToMany(
      mappedBy = "order",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  @JsonManagedReference
  private List<OrderLine> orderLines = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user")
  @ToString.Include
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private OrderStatus status;

  @Column(name = "total_amount")
  private Double totalAmount;

  @Column(name = "address")
  private String address;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "comment")
  private String comment;

  @Column(name = "order_date")
  private LocalDateTime orderDate;

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

  @PrePersist
  @PreUpdate
  public void prepPersist() {
    if (this.orderDate == null) {
      this.orderDate = LocalDateTime.now();
    }

    if (this.orderNumber == null) {
      this.orderNumber =
          String.format("ORDER-%s", UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    }

    if (this.status == null) {
      this.status = OrderStatus.EN_ATTENTE;
    }

    if (this.totalAmount == null) {
      calculateTotalAmount();
    }
  }

  public void addOrderLine(OrderLine orderLine) {
    if (orderLine != null && !this.orderLines.contains(orderLine)) {
      this.orderLines.add(orderLine);
      orderLine.setOrder(this);
      calculateTotalAmount();
    }
  }

  public void removeOrderLine(OrderLine orderLine) {
    if (this.orderLines.remove(orderLine)) {
      orderLine.setOrder(null);
      calculateTotalAmount();
    }
  }

  public void calculateTotalAmount() {
    this.totalAmount = 0.0;
    for (OrderLine line : this.orderLines) {
      if (line.getMedicine() != null && line.getMedicine().getNewPrice() != null) {
        this.totalAmount += line.getMedicine().getNewPrice() * line.getQuantity();
      }
    }
  }
}
