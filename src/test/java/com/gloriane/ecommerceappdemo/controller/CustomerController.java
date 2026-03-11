package com.gloriane.ecommerceappdemo.controller;

import org.junit.jupiter.api.Tag;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated //
@Tag(name = "Customer Controller", description = "APIs for managing Customers")
public class CustomerController {
}
