package com.example.cart.service;

import com.example.cart.client.ProductClient;
import com.example.cart.model.CartItem;
import com.example.cart.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllItems() {
        CartItem item = new CartItem();
        item.setId(1L);
        item.setProductIds(List.of(100L));
        item.setQuantity(2);
        item.setPrice(500.0);

        when(cartRepository.findAll()).thenReturn(List.of(item));

        List<CartItem> items = cartService.getAllItems();

        assertEquals(1, items.size());
        assertEquals(100L, items.get(0).getProductIds().get(0));
    }

    @Test
    void testAddItemSuccess() {
        CartItem item = new CartItem();
        item.setProductIds(List.of(100L));
        item.setQuantity(1);
        item.setPrice(250.0);

        when(productClient.getProductById(100L)).thenReturn(new com.example.cart.dto.ProductDTO());
        when(cartRepository.save(item)).thenReturn(item);

        CartItem saved = cartService.addItem(item);
        assertNotNull(saved);
        verify(productClient, times(1)).getProductById(100L);
        verify(cartRepository, times(1)).save(item);
    }

    @Test
    void testAddItemProductNotFound() {
        CartItem item = new CartItem();
        item.setProductIds(List.of(101L));
        item.setQuantity(1);
        item.setPrice(300.0);

        when(productClient.getProductById(101L)).thenThrow(new RuntimeException("Product not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartService.addItem(item);
        });

        assertTrue(exception.getMessage().contains("Product not found"));
    }

    @Test
    void testGetAllProductIds() {
        CartItem item1 = new CartItem();
        item1.setProductIds(List.of(1L, 2L));
        CartItem item2 = new CartItem();
        item2.setProductIds(List.of(2L, 3L));

        when(cartRepository.findAll()).thenReturn(List.of(item1, item2));

        List<Long> ids = cartService.getAllProductIds();
        assertEquals(3, ids.size());
        assertTrue(ids.containsAll(List.of(1L, 2L, 3L)));
    }
}
