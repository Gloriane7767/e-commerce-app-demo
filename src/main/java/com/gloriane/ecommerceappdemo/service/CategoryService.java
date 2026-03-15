package com.gloriane.ecommerceappdemo.service;

import com.gloriane.ecommerceappdemo.dto.request.CategoryRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto request);
}
