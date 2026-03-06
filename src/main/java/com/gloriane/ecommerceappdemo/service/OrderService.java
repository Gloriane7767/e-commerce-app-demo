package com.gloriane.ecommerceappdemo.service;

import com.gloriane.ecommerceappdemo.dto.request.OrderRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto requestDto);
    OrderResponseDto findOrderById(Long orderId);
    List<OrderResponseDto> findOrdersByCustomerId(Long customerId);
    OrderResponseDto updateOrderStatus(Long orderId, String status);
}
