package com.crashero.core.service;

import com.crashero.model.Cart;

import java.util.List;
import java.util.Optional;

public interface CartPort {
    Cart save(Cart cart);
    Optional<Cart> findById(Long id);
    void deleteById(Long id);
    List<Cart> findAll();
    Optional<Cart> findByUserId(Long userId);
}
