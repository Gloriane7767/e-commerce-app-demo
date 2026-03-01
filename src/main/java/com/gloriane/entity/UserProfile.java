package com.gloriane.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString // This annotation generates a toString() method for the class, which is useful for debugging and logging purposes.
@EqualsAndHashCode // This annotation generates equals() and hashCode() methods for the class, which are important for comparing objects and using them in collections.
@ Builder // This annotation generates a builder pattern for the class, allowing for more flexible and readable object creation.

@Entity // This annotation indicates that this class is a JPA entity and will be mapped to a database table.
@Table(name = "user_profiles") // This annotation specifies the name of the database table that this entity will be mapped to.
public class UserProfile {
    @Id // This annotation indicates that the field 'id' is the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This annotation specifies that the value of the primary key will be generated automatically by the database using an identity
    private Long id;

    @Column(length = 100) // This annotation specifies that the 'nickName' column in the database will have a maximum length of 100 characters.
    private String nickName;

    @Column(unique = true, length = 20)
    private String phoneNumber;

    @Column(length = 500)
    private String bio;

    @OneToOne(mappedBy = "userProfile") // This annotation indicates that this field is the inverse side of a one-to-one relationship with the Customer entity. The 'mappedBy' attribute specifies that the relationship is mapped by the 'userProfile' field in the Customer entity.
    private Customer customer; // This field represents the one-to-one relationship with the Customer entity. It will be mapped to a foreign key in the database.
}

