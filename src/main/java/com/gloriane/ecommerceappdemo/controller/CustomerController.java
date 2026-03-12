package com.gloriane.ecommerceappdemo.controller;

import com.gloriane.ecommerceappdemo.dto.request.CustomerRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.CustomerResponseDto;
import com.gloriane.ecommerceappdemo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController // "I'm a counter that handles web requests!"
@RequestMapping ("/api/v1/") // "Find me at this address"
@Validated
public class CustomerController {
    private final CustomerService customerService; // "My helper who does the actual work"

    // Constructor to get the helper
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody @Valid CustomerRequestDto request) {
        CustomerResponseDto response = customerService.registerCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDto> findById(@PathVariable Long id) {
        CustomerResponseDto response = customerService.findCustomerById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequestDto request) {
        CustomerResponseDto response = customerService.updateCustomer(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
