package com.crashero.core.service;

import com.crashero.common.exception.OrderException;
import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
public class OrderService {
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

        Order save = orderPort.save(order);

        invoiceService.generateInvoice(save);
        return save;
    }

    public List<Order> getOrders(Long userId) {
        return orderPort.findAllByUserId(userId);
    }

    public Order findOrderById(Long id) {
        return orderPort.findById(id).orElseThrow(() -> new OrderException("Order not found", HttpStatus.NOT_FOUND));
    }
}
