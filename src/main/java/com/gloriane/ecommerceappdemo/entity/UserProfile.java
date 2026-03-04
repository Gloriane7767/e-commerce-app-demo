package com.gloriane.ecommerceappdemo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Safe equals/hashCode
@ToString(onlyExplicitlyIncluded = true) // Safe toString

@Entity
@Table(name = "user_profiles") // Maps entity to "user_profiles" table
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(length = 100)
    @ToString.Include
    private String nickName;

    @Column(unique = true, length = 20)
    @ToString.Include
    private String phoneNumber;

    @Column(length = 500)
    @ToString.Include
    private String bio;

    @OneToOne(mappedBy = "userProfile") // Inverse side (FK lives on Customer.profile_id)
    private Customer customer; // Not in toString/equals to avoid recursion
}

