package com.example.cart.repository;

import com.example.cart.model.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<CartItem, Long> { }

