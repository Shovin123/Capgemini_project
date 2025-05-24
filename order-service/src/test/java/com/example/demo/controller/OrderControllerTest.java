package com.example.demo.controller;

import com.example.demo.client.CartClient;
import com.example.demo.entity.Order;
import com.example.demo.entity.Order.Status;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private CartClient cartClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPlaceOrder_Valid() throws Exception {
        Order order = Order.builder()
                .id(1L)
                .userId(1L)
                .productIds(List.of(100L))
                .quantity(1)
                .totalPrice(500.0)
                .status(Status.PLACED)
                .orderDate(LocalDateTime.now())
                .build();

        Mockito.when(cartClient.getAllProductIdsInCart()).thenReturn(List.of(100L));
        Mockito.when(orderService.placeOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    void testPlaceOrder_InvalidProduct() throws Exception {
        Order order = Order.builder()
                .id(1L)
                .userId(1L)
                .productIds(List.of(999L)) // not in cart
                .quantity(1)
                .totalPrice(500.0)
                .build();

        Mockito.when(cartClient.getAllProductIdsInCart()).thenReturn(List.of(100L)); // valid cart has only 100

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Product ID 999 not found in Cart"));
    }
}
