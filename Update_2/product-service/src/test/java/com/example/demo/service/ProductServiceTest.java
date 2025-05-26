package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private static Long testProductId;

    @Test
    @Order(1)
    void testSaveProduct() {
        Product product = new Product(1L, "Test Product", "Description", 10.5, 100, "Category");
        Product saved = productService.saveProduct(product);
        testProductId = saved.getId();
        assertNotNull(saved);
        assertEquals("Test Product", saved.getName());
    }

    @Test
    @Order(2)
    void testGetAllProducts() {
        List<Product> products = productService.getAll();
        assertFalse(products.isEmpty());
    }

    @Test
    @Order(3)
    void testGetProductById() {
        Product product = productService.getProductById(1L);
        assertNotNull(product);
        assertEquals("Test Product", product.getName());
    }

    @Test
    @Order(4)
    void testUpdateProduct() {
        Product updated = new Product(1L, "Updated Product", "New Desc", 15.0, 200, "New Category");
        Product result = productService.updateProduct(1L, updated);
        assertEquals("Updated Product", result.getName());
        assertEquals(15.0, result.getPrice());
    }

    @Test
    @Order(5)
    void testDeleteProduct() {
        productService.deleteById(1L);
        assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
    }
}
