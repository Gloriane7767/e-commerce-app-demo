package com.gloriane.ecommerceappdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Safe equals/hashCode
@ToString(onlyExplicitlyIncluded = true) // Safe toString

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(nullable = false)
    @ToString.Include
    private Integer quantity;

    @Column(nullable = false, precision = 19, scale = 2) // Snapshot price at time of purchase (recommended BigDecimal)
    @ToString.Include
    private BigDecimal priceAtPurchase;

    @ManyToOne(fetch = FetchType.LAZY) // Many order items reference one product
    @JoinColumn(name = "product_id", nullable = false) // FK order_items.product_id
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY) // Many order items belong to one order
    @JoinColumn(name = "order_id", nullable = false) // FK order_items.order_id
    private Order order;
}

