package com.gloriane.ecommerceappdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Safe equals/hashCode
@ToString(onlyExplicitlyIncluded = true) // Safe toString

@Entity // JPA entity
@Table(name = "categories") // Maps to "categories" table
public class Category {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @ToString.Include
    private String name;

    @OneToMany(mappedBy = "category")
    @ToString.Include
    private List<Product> products;
}