package com.gloriane.ecommerceappdemo.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String categoryName;
}
