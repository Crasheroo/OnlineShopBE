package com.crashero.core.service.impl;

import com.crashero.core.service.InvoicePort;
import com.crashero.core.service.InvoiceService;
import com.crashero.model.Invoice;
import com.crashero.model.Order;
import com.crashero.model.exception.OrderException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoicePort invoicePort;

    @Override
    public void generateInvoice(Order order) {
        System.err.println("Generating invoice for order ID: " + order.getId() + " currentUserId: " + order.getUserId());

        if (order.getUserId() == null) {
            System.err.println("Attempted to generate invoice for order " + order.getId() + " without userId");
            throw new IllegalArgumentException("Order must have a userId to generate invoice");
        }

        Invoice build = Invoice.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .amount(order.getTotalAmount())
                .issuedAt(LocalDateTime.now())
                .build();

        invoicePort.save(build);
    }

    @Override
    public List<Invoice> getInvoicesByUserId(Long userId) {
        return invoicePort.findAllByUserId(userId);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoicePort.findById(id)
                .orElseThrow(() -> new OrderException("Invoice not found"));
    }
}
