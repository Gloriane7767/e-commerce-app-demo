package com.gloriane.ecommerceappdemo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderRequestDto {
    @NotNull
    private Long customerId;
    
    @NotEmpty
    @Valid
    private List<OrderItemDto> items;
    
    public static class OrderItemDto {
        @NotNull
        private Long productId;
        
        @NotNull
        @Min(1)
        private Integer quantity;
    }
}
       