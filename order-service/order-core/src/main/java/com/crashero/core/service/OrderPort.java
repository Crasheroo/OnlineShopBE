package com.crashero.core.service;

import com.crashero.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderPort {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAllByUserId(Long userId);
}
