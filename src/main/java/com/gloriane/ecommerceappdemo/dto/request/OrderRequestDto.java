package com.gloriane.ecommerceappdemo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDto(
        @NotNull Long customerId,
        @NotEmpty List<OrderItemRequestDto> items
) {
    public record OrderItemRequestDto(
            @NotNull Long productId,
            @NotNull @Min(1) Integer quantity
    ) {}
}
