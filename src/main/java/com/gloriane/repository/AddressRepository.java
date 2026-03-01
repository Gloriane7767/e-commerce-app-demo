package com.gloriane.repository;

import com.gloriane.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByZipCode(String zipCode);
    List<Address> findByCity(String city);
    List<Address> findByStreetContaining(String street);
    long countByZipCodeStartingWith(String prefix);
}
