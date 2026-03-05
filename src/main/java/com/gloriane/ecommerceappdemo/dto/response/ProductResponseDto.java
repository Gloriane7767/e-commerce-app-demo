package com.gloriane.ecommerceappdemo.dto.response;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        BigDecimal price,
        String category // Nested DTO for category details, not just ID
) {}
