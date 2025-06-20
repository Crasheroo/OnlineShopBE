package com.crashero.user.adapters.config.fallback;

import com.crashero.model.Invoice;
import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import com.crashero.user.adapters.out.OrderClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OrderClientFallback implements OrderClient {
    @Override
    public List<Invoice> getInvoicesByUserId(Long userId) {
        log.error("getInvoicesByUserId fallback");
        throw new RuntimeException("Fallback to get invoices");
    }

    @Override
    public Order createOrder(Long userId, List<OrderItem> items) {
        log.error("createOrder fallback");
        throw new RuntimeException("Fallback to create order");
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        log.error("getOrdersByUserId fallback");
        throw new RuntimeException("Fallback to get orders");
    }
}
