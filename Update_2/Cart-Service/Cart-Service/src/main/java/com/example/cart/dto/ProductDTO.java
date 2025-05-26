// com.example.cart.dto.ProductDTO.java
package com.example.cart.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private int stock;
    private String category;
}
