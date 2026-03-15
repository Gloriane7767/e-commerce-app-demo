package com.gloriane.ecommerceappdemo.service;

import com.gloriane.ecommerceappdemo.dto.request.ProductRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.ProductResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto);
    List<ProductResponseDto> findAllProducts();

    @Transactional(readOnly = true) // Read-only transaction
    ProductResponseDto findProductByName(String productName);

    List<ProductResponseDto> searchByName(String name);
}
