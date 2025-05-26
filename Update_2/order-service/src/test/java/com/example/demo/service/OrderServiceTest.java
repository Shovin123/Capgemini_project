package com.example.demo.service;

import com.example.demo.entity.Order;

import com.example.demo.entity.Order.Status;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    static Long orderId;

    @Test
    @org.junit.jupiter.api.Order(1)
    void testPlaceOrder() {
        Order order = Order.builder()
                .id(1L)
                .userId(101L)
                .productIds(List.of(1L, 2L))
                .quantity(2)
                .totalPrice(500.0)
                .build();

        Order saved = orderService.placeOrder(order);
        orderId = saved.getId();

        assertNotNull(saved);
        assertEquals(Status.PLACED, saved.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void testGetOrderById() {
        Order order = orderService.getOrderById(orderId);
        assertNotNull(order);
        assertEquals(101L, order.getUserId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testGetAllOrders() {
        List<Order> all = orderService.getAllOrders();
        assertFalse(all.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void testCancelOrder() {
        Order cancelled = orderService.cancelOrder(orderId);
        assertEquals(Status.CANCELLED, cancelled.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void testDeleteOrder() {
        orderService.deleteOrder(orderId);
        assertNull(orderService.getOrderById(orderId));
    }
}
