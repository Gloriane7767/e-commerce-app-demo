package com.gloriane.ecommerceappdemo.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AddressResponseDto {
    private String street;
    private String city;
    private String zipCode;
}
