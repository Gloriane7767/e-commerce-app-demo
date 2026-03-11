package com.gloriane.ecommerceappdemo.service.serviceImpl;

import com.gloriane.ecommerceappdemo.dto.request.CustomerRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.CustomerResponseDto;
import com.gloriane.ecommerceappdemo.entity.Customer;
import com.gloriane.ecommerceappdemo.entity.UserProfile;
import com.gloriane.ecommerceappdemo.exception.DuplicateEntryException;
import com.gloriane.ecommerceappdemo.mapper.EntitytoDtoMapper;
import com.gloriane.ecommerceappdemo.repository.CustomerRepository;
import com.gloriane.ecommerceappdemo.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for Customer operations.
 * Handles business logic for customer registration, retrieval, and updates.
 * Uses @Transactional to ensure database consistency.
 */
@Service // Marks this as a Spring service component for dependency injection
public class CustomerServiceImpl implements CustomerService {
    // Dependencies injected via constructor
    private final CustomerRepository customerRepository; // Database access for Customer entity
    private final EntitytoDtoMapper mapper; // Converts between entities and DTOs

    // Constructor injection (recommended over field injection)
    public CustomerServiceImpl(CustomerRepository customerRepository, EntitytoDtoMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    /**
     * Registers a new customer in the system.
     * Flow: Validate → Check duplicate email → Map DTO to Entity → Save → Map Entity to DTO
     * @param requestDto Contains customer data (firstName, lastName, email, address)
     * @return CustomerResponseDto with saved customer data including generated ID
     * @throws IllegalArgumentException if requestDto is null
     * @throws DuplicateEntryException if email already exists
     */
    @Override
    @Transactional // Ensures all database operations succeed or rollback together
    public CustomerResponseDto registerCustomer(CustomerRequestDto requestDto) {
        // Step 1: Validate input
        if(requestDto == null) {
            throw new IllegalArgumentException("CustomerRequestDto cannot be null");
        }

        // Step 2: Check for duplicate email (business rule: emails must be unique)
        if(customerRepository.existsByEmail(requestDto.email())) {
            throw new DuplicateEntryException("Customer with email already exists");
        }

        // Step 3: Convert DTO to Entity using mapper (includes Address creation)
        Customer customer = mapper.toCustomerEntity(requestDto);
        
        // Step 4: Create and link UserProfile (required by Customer entity)
        customer.setUserProfile(new UserProfile());
        
        // Step 5: Save to database (cascades to Address and UserProfile)
        Customer savedCustomer = customerRepository.save(customer);
        
        // Step 6: Convert saved Entity back to DTO for response
        return mapper.toCustomerResponseDto(savedCustomer);
    }

    /**
     * Finds a customer by their ID.
     * Flow: Find by ID → Map Entity to DTO
     * @param customerId The customer's unique identifier
     * @return CustomerResponseDto with customer data
     * @throws RuntimeException if customer not found
     */
    @Override
    @Transactional(readOnly = true) // Read-only transaction for performance optimization
    public CustomerResponseDto findCustomerById(Long customerId) {
        // Step 1: Find customer or throw exception if not found
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        // Step 2: Convert Entity to DTO
        return mapper.toCustomerResponseDto(customer);
    }

    /**
     * Updates an existing customer's information.
     * Flow: Find customer → Update fields → Save → Map Entity to DTO
     * @param customerId The customer's unique identifier
     * @param requestDto Contains updated customer data
     * @return CustomerResponseDto with updated customer data
     * @throws RuntimeException if customer not found
     */
    @Override
    @Transactional // Write transaction to persist changes
    public CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto requestDto) {
        // Step 1: Find existing customer or throw exception
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        // Step 2: Update customer fields from DTO
        customer.setFirstname(requestDto.firstName());
        customer.setLastname(requestDto.lastName());
        customer.setEmail(requestDto.email());
        
        // Step 3: Update nested Address entity fields
        customer.getAddress().setStreet(requestDto.street());
        customer.getAddress().setCity(requestDto.city());
        customer.getAddress().setZipCode(requestDto.zipCode());

        // Step 4: Save updated entity (JPA detects changes and updates database)
        Customer updatedCustomer = customerRepository.save(customer);

        // Step 5: Convert updated Entity to DTO
        return mapper.toCustomerResponseDto(updatedCustomer);
    }
}
