package com.gloriane.ecommerceappdemo.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDto(
        @NotBlank String name,
        @NotNull @DecimalMin("0.01") BigDecimal price,
        @NotNull Long categoryId  // ← Just the ID, not nested
) {} // Canonical Constructor
