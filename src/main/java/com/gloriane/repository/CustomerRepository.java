package com.gloriane.repository;

import com.gloriane.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    List<Customer> findByLastnameIgnoreCase(String lastName);
    List<Customer> findByAddressCity(String city);
    List<Customer> findByEmailContainingIgnoreCase(String keyword);
    List<Customer> findByCreatedAtAfter(Instant createdAt);
    List<Customer> findByCreatedAtBetween(Instant createdAt, Instant createdAt2);
    long countByAddressCity(String city);
    boolean existsByEmail(String email);
}