package com.search.pharmacy.domain.model;

import com.search.pharmacy.common.exception.orm.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Date;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "MEDICINE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Medicine extends AbstractEntity<Long> {

    @Column(name = "name")
    @ToString.Include
    private String name;

    @Column(name = "description")
    @ToString.Include
    private String description;
}
