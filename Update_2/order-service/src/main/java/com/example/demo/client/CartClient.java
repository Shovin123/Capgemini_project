package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "cart-service")
public interface CartClient {
    @GetMapping("/api/cart/product-ids")
    List<Long> getAllProductIdsInCart();
}
