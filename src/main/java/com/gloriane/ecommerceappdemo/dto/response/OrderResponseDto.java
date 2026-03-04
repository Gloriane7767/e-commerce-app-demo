package com.gloriane.ecommerceappdemo.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDto {
    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItemResponseDto> items;
}
