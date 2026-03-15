package com.gloriane.ecommerceappdemo.controller;

import com.gloriane.ecommerceappdemo.dto.request.ProductRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.ProductResponseDto;
import com.gloriane.ecommerceappdemo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    // Constructor injection - Spring automatically provides the service
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody @Valid ProductRequestDto request) {

        ProductResponseDto response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {

        List<ProductResponseDto> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProducts(
            @RequestParam String name) {

        List<ProductResponseDto> products = productService.searchByName(name);
        return ResponseEntity.ok(products);
    }

    }
