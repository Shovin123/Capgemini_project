package com.example.cart.controller;

import com.example.cart.model.CartItem;
import com.example.cart.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    private CartItem sampleItem;

    @BeforeEach
    void setUp() {
        sampleItem = new CartItem();
        sampleItem.setId(1L);
        sampleItem.setProductIds(List.of(100L, 200L));
        sampleItem.setQuantity(2);
        sampleItem.setPrice(500.0);
    }

    @Test
    void testGetAllItems() throws Exception {
        when(cartService.getAllItems()).thenReturn(List.of(sampleItem));

        mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testGetItemById() throws Exception {
        when(cartService.getItemById(1L)).thenReturn(Optional.of(sampleItem));

        mockMvc.perform(get("/api/cart/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testAddItem() throws Exception {
        when(cartService.addItem(any(CartItem.class))).thenReturn(sampleItem);

        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateItem() throws Exception {
        when(cartService.updateItem(any(CartItem.class))).thenReturn(sampleItem);

        mockMvc.perform(put("/api/cart/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeleteItem() throws Exception {
        doNothing().when(cartService).deleteItem(1L);

        mockMvc.perform(delete("/api/cart/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllProductIdsInCart() throws Exception {
        when(cartService.getAllProductIds()).thenReturn(List.of(100L, 200L));

        mockMvc.perform(get("/api/cart/product-ids"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }
}
