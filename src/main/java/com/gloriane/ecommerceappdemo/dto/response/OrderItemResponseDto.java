package com.gloriane.ecommerceappdemo.dto.response;

import java.math.BigDecimal;

public record OrderItemResponseDto(
    Long productId,
    String productName,
    Integer quantity,
    BigDecimal priceAtPurchase
) {}
