package com.gloriane.ecommerceappdemo.service;

import com.gloriane.ecommerceappdemo.dto.request.CustomerRequestDto;
import com.gloriane.ecommerceappdemo.dto.response.CustomerResponseDto;
import java.util.List;

public interface CustomerService {
    CustomerResponseDto registerCustomer(CustomerRequestDto requestDto);
    CustomerResponseDto findCustomerById(Long customerId);
    CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto requestDto);
}
