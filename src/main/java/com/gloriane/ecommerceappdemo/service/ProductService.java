package com.gloriane.ecommerceappdemo.service;

import com.gloriane.ecommerceappdemo.dto.request.ProductRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto);
    List<ProductResponseDto> findAllProducts();
    ProductResponseDto findProductByName(String productName);

}
