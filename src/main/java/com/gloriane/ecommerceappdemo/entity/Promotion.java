package com.gloriane.ecommerceappdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Safe equals/hashCode
@ToString(onlyExplicitlyIncluded = true) // Safe toString

@Entity // JPA entity
@Table(name = "promotions") // Maps to "promotions" table
public class Promotion {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 50)  // Promotion code
    @ToString.Include
    private String code;

    @Column(nullable = false)
    @ToString.Include
    private LocalDate startDate;

    @Column(nullable = false)
    @ToString.Include
    private LocalDate endDate;

    @ManyToMany(mappedBy = "promotions")
    private Set<Product> products;
}
