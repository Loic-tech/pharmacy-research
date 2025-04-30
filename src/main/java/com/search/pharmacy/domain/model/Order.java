package com.search.pharmacy.domain.model;


import com.search.pharmacy.common.exception.orm.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_order"))
public class Order extends AbstractEntity<Long> {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "orders_cart", joinColumns = @JoinColumn(name = "id_order"), inverseJoinColumns = @JoinColumn(name = "id_cart"))
    private List<Cart> cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @ToString.Include
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "comment")
    private String comment;

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


