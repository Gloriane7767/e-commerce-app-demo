package com.gloriane.ecommerceappdemo.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class ProductRequestDto {
    String name;
    double price;
    Long categoryId;
}
