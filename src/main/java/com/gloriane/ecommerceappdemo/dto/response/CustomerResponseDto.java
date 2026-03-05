package com.gloriane.ecommerceappdemo.dto.response;

public record CustomerResponseDto(
    Long id,
    String fullName,
    String email,
    AddressResponseDto addressResponse // nested DTO for address details
) {}
