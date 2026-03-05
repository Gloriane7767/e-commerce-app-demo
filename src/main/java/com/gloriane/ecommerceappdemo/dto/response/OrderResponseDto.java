package com.gloriane.ecommerceappdemo.dto.response;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        LocalDateTime orderDate,
        String status,
        String customerName,
        List<OrderItemResponseDto> orderItems,  // ← List of nested records
        BigDecimal totalAmount
) {}
