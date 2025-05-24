package com.example.cart.service;

import com.example.cart.model.CartItem;
import com.example.cart.repository.CartRepository;
import com.example.cart.client.ProductClient;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CartRepository repository;
    private final ProductClient productClient;
    
    public CartService(CartRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    public List<CartItem> getAllItems() {
        return repository.findAll();
    }

    public CartItem addItem(CartItem item) {
        // Validate each product ID before adding
        for (Long productId : item.getProductIds()) {
            try {
                productClient.getProductById(productId); // Should return Product, not boolean
            } catch (Exception e) {
                logger.warn("Product ID {} not found in Product Service", productId);
                throw new RuntimeException("Product not found: " + productId);
            }
        }

        return repository.save(item);
    }

    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    public Optional<CartItem> getItemById(Long id) {
        return repository.findById(id);
    }

    public CartItem updateItem(CartItem updatedItem) {
        Optional<CartItem> existing = repository.findById(updatedItem.getId());
        if (existing.isPresent()) {
            return repository.save(updatedItem);
        } else {
            throw new RuntimeException("CartItem not found with id " + updatedItem.getId());
        }
    }
    
    public List<Long> getAllProductIds() {
        List<CartItem> items = repository.findAll();
        return items.stream()
                    .flatMap(item -> item.getProductIds().stream())
                    .distinct()
                    .toList();
    }

}
