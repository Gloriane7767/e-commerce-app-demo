package com.gloriane.ecommerceappdemo.service.serviceImpl;

import com.gloriane.ecommerceappdemo.dto.request.OrderRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.OrderResponseDto;
import com.gloriane.ecommerceappdemo.entity.*;
import com.gloriane.ecommerceappdemo.exception.ResourceNotFoundException;
import com.gloriane.ecommerceappdemo.mapper.EntitytoDtoMapper;
import com.gloriane.ecommerceappdemo.repository.CustomerRepository;
import com.gloriane.ecommerceappdemo.repository.OrderRepository;
import com.gloriane.ecommerceappdemo.repository.ProductRepository;
import com.gloriane.ecommerceappdemo.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service implementation for Order operations.
 * Handles complex business logic for order creation including:
 * - Customer validation
 * - Product validation for each order item
 * - Price capture at time of purchase (priceAtPurchase)
 * - Optional promotion application
 * - Transactional order persistence with items
 */
@Service // Spring service component
public class OrderServiceImpl implements OrderService {
    // Dependencies for database access and mapping
    private final OrderRepository orderRepository; // Order database operations
    private final CustomerRepository customerRepository; // Customer validation
    private final ProductRepository productRepository; // Product validation
    private final EntitytoDtoMapper mapper; // Entity-DTO conversions

    // Constructor injection of all dependencies
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, 
                           ProductRepository productRepository, EntitytoDtoMapper mapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    /**
     * Creates a new order with multiple items.
     * Flow: Validate customer → Create order → For each item: validate product, capture price, apply promotions → Save order
     * @param requestDto Contains customerId and list of items (productId, quantity)
     * @return OrderResponseDto with saved order data including all items and total
     * @throws ResourceNotFoundException if customer or any product not found
     */
    @Override
    @Transactional // Critical: ensures all-or-nothing persistence (order + all items saved together or rolled back)
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        // Step 1: Find and validate the Customer exists
        Customer customer = customerRepository.findById(requestDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + requestDto.customerId()));

        // Step 2: Create new Order entity and link to customer
        Order order = new Order();
        order.setCustomer(customer); // Establishes ManyToOne relationship
        order.setStatus(OrderStatus.CREATED); // Initial status

        // Step 3: Process each requested item
        for (OrderRequestDto.OrderItemRequestDto itemRequest : requestDto.items()) {
            // Step 3a: Find and validate the Product exists
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemRequest.productId()));

            // Step 3b: Apply any active promotions to get final price (optional business logic)
            BigDecimal finalPrice = applyPromotions(product);

            // Step 3c: Create OrderItem and capture price at purchase time
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product); // Links to Product entity
            orderItem.setQuantity(itemRequest.quantity());
            orderItem.setPriceAtPurchase(finalPrice); // CRITICAL: captures price snapshot (not current product price)

            // Step 3d: Add item to order (maintains bidirectional relationship)
            order.addItem(orderItem); // Helper method sets orderItem.setOrder(this)
        }

        // Step 4: Save the Order (cascades to all OrderItems due to CascadeType.ALL)
        Order savedOrder = orderRepository.save(order);

        // Step 5: Convert saved entity to DTO (includes customer name, items, total)
        return mapper.toOrderResponseDto(savedOrder);
    }

    /**
     * Applies active promotions to a product's price.
     * Checks if product has promotions and if they are currently active based on date range.
     * @param product The product to check for promotions
     * @return Final price after applying promotions (or original price if no active promotions)
     */
    private BigDecimal applyPromotions(Product product) {
        BigDecimal price = product.getPrice(); // Start with current product price
        
        // Check if product has any promotions linked (ManyToMany relationship)
        if (product.getPromotions() != null && !product.getPromotions().isEmpty()) {
            LocalDate today = LocalDate.now();
            
            // Iterate through all promotions
            for (Promotion promotion : product.getPromotions()) {
                // Check if promotion is active based on date range
                if (!today.isBefore(promotion.getStartDate()) && !today.isAfter(promotion.getEndDate())) {
                    // Promotion is active - apply discount logic here
                    // Note: Current Promotion entity doesn't have discount percentage field
                    // This is a placeholder for future discount calculation
                }
            }
        }
        
        return price; // Return final price (with or without promotions applied)
    }

    /**
     * Finds an order by its ID.
     * Flow: Find by ID → Map to DTO
     * @param orderId The order's unique identifier
     * @return OrderResponseDto with order details
     * @throws ResourceNotFoundException if order not found
     */
    @Override
    @Transactional(readOnly = true) // Read-only transaction
    public OrderResponseDto findOrderById(Long orderId) {
        // Find order or throw exception
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Convert to DTO
        return mapper.toOrderResponseDto(order);
    }

    /**
     * Finds all orders for a specific customer.
     * Flow: Find by customer ID → Stream → Map each to DTO → Collect to List
     * @param customerId The customer's unique identifier
     * @return List of OrderResponseDto for the customer
     */
    @Override
    @Transactional(readOnly = true) // Read-only transaction
    public List<OrderResponseDto> findOrdersByCustomerId(Long customerId) {
        // Use repository method to find orders by customer (uses Customer_Id for ManyToOne relationship)
        List<Order> orders = orderRepository.findByCustomer_Id(customerId);
        
        // Convert each order entity to DTO
        return orders.stream()
                .map(mapper::toOrderResponseDto)
                .toList();
    }

    /**
     * Updates the status of an existing order.
     * Flow: Find order → Update status → Save → Map to DTO
     * @param orderId The order's unique identifier
     * @param status The new status (CREATED, PAID, SHIPPED, CANCELLED)
     * @return OrderResponseDto with updated order data
     * @throws ResourceNotFoundException if order not found
     */
    @Override
    @Transactional // Write transaction to persist status change
    public OrderResponseDto updateOrderStatus(Long orderId, String status) {
        // Step 1: Find existing order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Step 2: Update status (converts String to OrderStatus enum)
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        
        // Step 3: Save updated order
        Order updatedOrder = orderRepository.save(order);

        // Step 4: Convert to DTO
        return mapper.toOrderResponseDto(updatedOrder);
    }
}
