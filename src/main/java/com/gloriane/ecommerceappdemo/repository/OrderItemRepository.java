package com.gloriane.ecommerceappdemo.repository;

import com.gloriane.ecommerceappdemo.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder_Id(Long orderId);
    List<OrderItem> findByProduct_Id(Long productId);
    List<OrderItem> findByQuantityGreaterThan(Integer quantity);
}
