package com.example.cart.controller;

import com.example.cart.model.CartItem;
import com.example.cart.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public List<CartItem> getAllItems() {
        return service.getAllItems();
    }

    @GetMapping("/{id}")
    public Optional<CartItem> getItem(@PathVariable Long id) {
        return service.getItemById(id);
    }

    @PostMapping
    public CartItem addItem(@RequestBody CartItem item) {
        return service.addItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
    }
    
    @PutMapping("/{id}")
    public CartItem updateItem(@PathVariable Long id, @RequestBody CartItem updatedItem) {
        // Set the id to make sure you update the correct item
        updatedItem.setId(id);
        return service.updateItem(updatedItem);
    }
    
    @GetMapping("/product-ids")
    public List<Long> getAllProductIdsInCart() {
        return service.getAllProductIds();
    }


}
