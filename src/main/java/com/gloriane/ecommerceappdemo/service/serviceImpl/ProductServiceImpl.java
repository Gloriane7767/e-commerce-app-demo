package com.gloriane.ecommerceappdemo.service.serviceImpl;

import com.gloriane.ecommerceappdemo.dto.request.ProductRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.ProductResponseDto;
import com.gloriane.ecommerceappdemo.entity.Category;
import com.gloriane.ecommerceappdemo.entity.Product;
import com.gloriane.ecommerceappdemo.exception.ResourceNotFoundException;
import com.gloriane.ecommerceappdemo.mapper.EntitytoDtoMapper;
import com.gloriane.ecommerceappdemo.repository.CategoryRepository;
import com.gloriane.ecommerceappdemo.repository.ProductRepository;
import com.gloriane.ecommerceappdemo.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for Product operations.
 * Handles business logic for product creation, retrieval, and search.
 * Validates that products are linked to valid categories.
 */
@Service // Spring service component
public class ProductServiceImpl implements ProductService {
    // Dependencies for database access and mapping
    private final ProductRepository productRepository; // Product database operations
    private final CategoryRepository categoryRepository; // Category validation
    private final EntitytoDtoMapper mapper; // Entity-DTO conversions

    // Constructor injection of dependencies
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, EntitytoDtoMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    /**
     * Creates a new product in the system.
     * Flow: Validate category exists → Map DTO to Entity → Link category → Save → Map to DTO
     * @param requestDto Contains product data (name, price, categoryId)
     * @return ProductResponseDto with saved product data including category name
     * @throws ResourceNotFoundException if category not found
     */
    @Override
    @Transactional // Write transaction for database consistency
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        // Step 1: Validate that the category exists (business rule: products must have valid category)
        Category category = categoryRepository.findById(requestDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + requestDto.categoryId()));

        // Step 2: Convert DTO to Product entity (sets name and price)
        Product product = mapper.toProductEntity(requestDto);
        
        // Step 3: Link the product to the validated category (establishes ManyToOne relationship)
        product.setCategory(category);

        // Step 4: Save product to database (generates ID)
        Product savedProduct = productRepository.save(product);

        // Step 5: Convert saved entity to DTO (includes category name)
        return mapper.toProductResponseDto(savedProduct);
    }

    /**
     * Retrieves all products from the database.
     * Flow: Fetch all → Stream → Map each to DTO → Collect to List
     * @return List of ProductResponseDto containing all products
     */
    @Override
    @Transactional(readOnly = true) // Read-only for performance
    public List<ProductResponseDto> findAllProducts() {
        // Fetch all products, convert each to DTO using mapper, collect to list
        return productRepository.findAll().stream()
                .map(mapper::toProductResponseDto) // Method reference: same as product -> mapper.toProductResponseDto(product)
                .toList();
    }

    /**
     * Searches for products by name (case-insensitive, partial match).
     * Flow: Search by name → Validate results → Return first match
     * @param productName The name or partial name to search for
     * @return ProductResponseDto of the first matching product
     * @throws ResourceNotFoundException if no products found
     */
    @Override
    @Transactional(readOnly = true) // Read-only transaction
    public ProductResponseDto findProductByName(String productName) {
        // Step 1: Search using repository method (case-insensitive, partial match)
        List<Product> products = productRepository.findByNameContainingIgnoreCase(productName);
        
        // Step 2: Validate that at least one product was found
        if(products.isEmpty()) {
            throw new ResourceNotFoundException("No products found with name: " + productName);
        }
        
        // Step 3: Return first matching product as DTO
        return mapper.toProductResponseDto(products.get(0));
    }
}
