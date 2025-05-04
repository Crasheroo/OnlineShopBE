package com.crashero.core.service;

import com.crashero.model.CartItem;

import java.util.Optional;

public interface CartItemPort {
    CartItem save(CartItem cartItem);
    Optional<CartItem> findById(Long id);
    void deleteById(Long id);
}
