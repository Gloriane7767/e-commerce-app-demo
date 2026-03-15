package com.gloriane.ecommerceappdemo.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(
        @NotBlank String name
) {}
