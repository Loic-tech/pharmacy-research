package com.search.pharmacy.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MEDICINE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @ToString.Include
    private String name;

    @Column(name = "description")
    @ToString.Include
    private String description;
}
