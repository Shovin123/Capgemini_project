package com.example.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.cart.dto.ProductDTO;



@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/products/view/{id}")
    ProductDTO getProductById(@PathVariable("id") Long id);
}
