package com.gloriane.ecommerceappdemo.service.serviceImpl;

import com.gloriane.ecommerceappdemo.dto.request.CategoryRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.CategoryResponseDto;
import com.gloriane.ecommerceappdemo.entity.Category;
import com.gloriane.ecommerceappdemo.exception.DuplicateEntryException;
import com.gloriane.ecommerceappdemo.repository.CategoryRepository;
import com.gloriane.ecommerceappdemo.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateEntryException("Category already exists: " + request.name());
        }

        Category category = new Category();
        category.setName(request.name());

        Category saved = categoryRepository.save(category);
        return new CategoryResponseDto(saved.getId(), saved.getName());
    }
}
