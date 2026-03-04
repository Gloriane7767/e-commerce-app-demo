package com.gloriane.ecommerceappdemo.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CustomerResponseDto {
    Long id;
    String fullName;
    String email;
    AddressResponseDto addressResponse;
}
