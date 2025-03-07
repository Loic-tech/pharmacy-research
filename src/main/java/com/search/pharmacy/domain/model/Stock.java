package com.search.pharmacy.domain.model;

import com.search.pharmacy.common.exception.orm.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "STOCK")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Stock extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pharmacy")
    @EqualsAndHashCode.Include
    private Pharmacy pharmacy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medicine")
    @EqualsAndHashCode.Include
    private Medicine medicine;

    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer quantity;
}
