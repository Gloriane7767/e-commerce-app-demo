package com.gloriane.ecommerceappdemo.entity;

import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Safe equals/hashCode
@ToString(onlyExplicitlyIncluded = true) // Safe toString (no relationships)


@Entity // JPA entity
@Table(name = "products") // Maps to "products" table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(nullable = false, length = 150)
    @ToString.Include
    private String name;

    @ElementCollection // Store the elements of this collection in a table named product_images.
    @CollectionTable( // The join column product_id links each image row to the owning product row (the product's primary key).
            name = "product_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "image_url", length = 500) // The column that stores the String values in the product_images table will be named image_url.
    private List<String> imageUrls = new ArrayList<>();

    @Column(nullable = false, precision = 19, scale = 2) // Recommended precision for money  @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY) // Many products belong to one category (avoid default EAGER)
    @JoinColumn(name = "category_id", nullable = false) // FK products.category_id
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY) // Many-to-many between products and promotions
    @JoinTable(
            name = "products_promotions", // Join table name
            joinColumns = @JoinColumn(name = "product_id"),  // FK to product
            inverseJoinColumns = @JoinColumn(name = "promotion_id") // FK to promotion
    )
    private Set<Promotion> promotions;

    // Convenience helpers to keep both sides in sync (recommended if bidirectional)
    public void addPromotion(Promotion promotion) {
        promotions.add(promotion);
        //promotion.getProducts().add(this);
    }

    public void removePromotion(Promotion promotion) {
        promotions.remove(promotion);
        //promotion.getProducts().remove(this);
    }
}
