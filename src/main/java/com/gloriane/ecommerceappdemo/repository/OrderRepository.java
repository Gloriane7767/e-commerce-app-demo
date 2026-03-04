package com.gloriane.ecommerceappdemo.repository;

import com.gloriane.ecommerceappdemo.entity.Order;
import com.gloriane.ecommerceappdemo.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer_Id(Long customerId);

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.status = :status")
    List<Order> findByStatus(@Param("status") OrderStatus status);

    List<Order> findByOrderDateAfter(Instant date);

    List<Order> findByOrderDateBetween(Instant start, Instant end);

    long countByStatus(OrderStatus status);

    List<Order> findByCustomer_IdAndStatus(Long customerId, OrderStatus status);
}


