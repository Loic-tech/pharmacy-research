package com.search.pharmacy.domain.model;


import com.search.pharmacy.common.exception.orm.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_cart"))
public class Cart extends AbstractEntity<Long> {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "cart_medicine", joinColumns = @JoinColumn(name = "id_cart"), inverseJoinColumns = @JoinColumn(name = "id_medicine"))
    private List<Medicine> medicines;
}
