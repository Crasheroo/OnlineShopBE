package com.crashero.core.service;

import com.crashero.model.Order;
import com.crashero.model.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Long userId, List<OrderItem> orderItems);

    List<Order> getOrders(Long userId);

    Order findOrderById(Long id);
}
