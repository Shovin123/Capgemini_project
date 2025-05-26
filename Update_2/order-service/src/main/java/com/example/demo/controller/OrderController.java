package com.example.demo.controller;

import com.example.demo.client.CartClient;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartClient cartClient;

    @Autowired
    private RestTemplate restTemplate;

    private final String PRODUCT_SERVICE_URL = "http://localhost:8081/products/view/";


//    @PostMapping
//    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
//        String url = PRODUCT_SERVICE_URL + order.getProductId();
//        try {
//            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
//            if (!response.getStatusCode().is2xxSuccessful()) {
//                return ResponseEntity.badRequest().body("Invalid product ID");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Product not found in Product Service");
//        }
//
//        Order savedOrder = orderService.placeOrder(order);
//        return ResponseEntity.ok(savedOrder);
//    }
    
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        List<Long> validProductIds = cartClient.getAllProductIdsInCart();

        for (Long productId : order.getProductIds()) {
            if (!validProductIds.contains(productId)) {
                return ResponseEntity.badRequest().body("Product ID " + productId + " not found in Cart");
            }
        }

        Order savedOrder = orderService.placeOrder(order);
        return ResponseEntity.ok(savedOrder);
    }


    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
