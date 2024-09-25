package com.search.pharmacy.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PHARMACY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "contact")
    private String contact;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
}
