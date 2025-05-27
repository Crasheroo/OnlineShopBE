package com.crashero.user.core.port.out;

import com.crashero.model.Invoice;
import com.crashero.model.Order;
import com.crashero.model.OrderItem;

import java.util.List;

public interface OrderPort {
    List<Invoice> getInvoicesByUserId(Long userId);

    Order createOrder(Long userId, List<OrderItem> items);

    List<Order> getOrdersByUserId(Long userId);
}
