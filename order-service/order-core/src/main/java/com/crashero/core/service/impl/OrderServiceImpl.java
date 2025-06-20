package com.crashero.core.service.impl;

import com.crashero.core.service.InvoiceService;
import com.crashero.core.service.OrderPort;
import com.crashero.core.service.OrderService;
import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import com.crashero.model.exception.OrderException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderPort orderPort;
    private final InvoiceService invoiceService;

    public Order createOrder(Long userId, List<OrderItem> orderItems) {

        double total = orderItems.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();

        Order order = Order.builder()
                .userId(userId)
                .items(orderItems)
                .totalAmount(total)
                .build();


        Order savedOrder = orderPort.save(order);


        if (savedOrder.getUserId() == null) {
            throw new OrderException("Order was saved without a user ID");
        }

        invoiceService.generateInvoice(savedOrder);
        return savedOrder;
    }

    public List<Order> getOrders(Long userId) {
        return orderPort.findAllByUserId(userId);
    }

    public Order findOrderById(Long id) {
        return orderPort.findById(id).orElseThrow(() -> new OrderException("Order not found"));
    }
}
