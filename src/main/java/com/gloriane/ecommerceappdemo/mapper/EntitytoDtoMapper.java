package com.gloriane.ecommerceappdemo.mapper;

import com.gloriane.ecommerceappdemo.dto.request.CustomerRequestDto;
import com.gloriane.ecommerceappdemo.dto.request.ProductRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.AddressResponseDto;
import com.gloriane.ecommerceappdemo.dto.response.CustomerResponseDto;
import com.gloriane.ecommerceappdemo.dto.response.OrderItemResponseDto;
import com.gloriane.ecommerceappdemo.dto.response.OrderResponseDto;
import com.gloriane.ecommerceappdemo.dto.response.ProductResponseDto;
import com.gloriane.ecommerceappdemo.entity.Address;
import com.gloriane.ecommerceappdemo.entity.Customer;
import com.gloriane.ecommerceappdemo.entity.Order;
import com.gloriane.ecommerceappdemo.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component // Spring automatically creates one instance of EntitytoDtoMapper at startup
public class EntitytoDtoMapper {

    // REQUEST → ENTITY: Convert CustomerRequestDto to Customer entity
    public Customer toCustomerEntity(CustomerRequestDto requestDto) {
        Address address = new Address();
        address.setStreet(requestDto.street());
        address.setCity(requestDto.city());
        address.setZipCode(requestDto.zipCode());

        Customer customer = new Customer();
        customer.setFirstname(requestDto.firstName());
        customer.setLastname(requestDto.lastName());
        customer.setEmail(requestDto.email());
        customer.setAddress(address);

        return customer;
    }

    // ENTITY → RESPONSE: Convert Customer entity to CustomerResponseDto
    public CustomerResponseDto toCustomerResponseDto(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null");

        AddressResponseDto addressDto = new AddressResponseDto(
                customer.getAddress().getStreet(),
                customer.getAddress().getCity(),
                customer.getAddress().getZipCode()
        );

        return new CustomerResponseDto(
                customer.getId(),
                customer.getFirstname() + " " + customer.getLastname(),
                customer.getEmail(),
                addressDto
        );
    }

    // REQUEST → ENTITY: Convert ProductRequestDto to Product entity
    public Product toProductEntity(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setName(requestDto.name());
        product.setPrice(requestDto.price());
        return product;
    }

    // ENTITY → RESPONSE: Convert Product entity to ProductResponseDto
    public ProductResponseDto toProductResponseDto(Product product) {
        String categoryName = null;
        if (product.getCategory() != null) {
            categoryName = product.getCategory().getName();
        }
        
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                categoryName
        );
    }

    // ENTITY → RESPONSE: Convert Order entity to OrderResponseDto
    public OrderResponseDto toOrderResponseDto(Order order) {
        List<OrderItemResponseDto> itemDtos = order.getOrderItems().stream()
                .map(item -> new OrderItemResponseDto(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtPurchase()
                ))
                .toList();

        BigDecimal total = order.getOrderItems().stream()
                .map(item -> item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDateTime orderDateTime = order.getOrderDate().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return new OrderResponseDto(
                order.getId(),
                orderDateTime,
                order.getStatus().toString(),
                order.getCustomer().getFirstname() + " " + order.getCustomer().getLastname(),
                itemDtos,
                total
        );
    }
}
