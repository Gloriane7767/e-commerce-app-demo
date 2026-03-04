package com.gloriane.ecommerceappdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true) // Safe equals/hashCode
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Safe toString (no relationships)

@Entity // JPA Entity
@Table(name="Customers") // Table name
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(nullable = false, length = 100)
    @ToString.Include
    private String firstname;

    @Column(nullable = false, length = 100)
    @ToString.Include
    private String lastname;

    @Column(nullable = false, unique = true, length = 100)
    @ToString.Include
    private String email;

    private Instant createdAt;
    @ToString.Include

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // Customer owns Address lifecycle
    @JoinColumn(name = "address_id", nullable = false) // FK column customers.address_id
    private Address address; // Not included in toString/equals

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // Customer owns UserProfile lifecycle
    @JoinColumn(name = "user_profile_id") // FK column customers.user_profile_id
    private UserProfile userProfile; // Not included in toString/equals

    // Keeps the bidirectional relation consistent (recommended.This code keeps both sides in sync.)
    public void setUserProfile(UserProfile userProfile) {
        // Break old link
        if (this.userProfile != null) {
            this.userProfile.setCustomer(null);
        }

        // Set new link
        this.userProfile = userProfile;

        // Maintain inverse side
        if (userProfile != null) {
            userProfile.setCustomer(this);
        }
    }
    @PrePersist
    private void prePersist() {
        createdAt = Instant.now();
    }
}
