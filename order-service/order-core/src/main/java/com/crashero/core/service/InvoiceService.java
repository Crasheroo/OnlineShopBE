package com.crashero.core.service;

import com.crashero.model.Invoice;
import com.crashero.model.Order;
import com.crashero.model.exception.OrderException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class InvoiceService {
    private final InvoicePort invoicePort;

    public void generateInvoice(Order order) {
        Invoice build = Invoice.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .amount(order.getTotalAmount())
                .issuedAt(LocalDateTime.now())
                .build();

        invoicePort.save(build);
    }

    public List<Invoice> getInvoicesByUserId(Long userId) {
        return invoicePort.findAllByUserId(userId);
    }

    public Invoice getInvoiceById(Long id) {
        return invoicePort.findById(id)
                .orElseThrow(() -> new OrderException("Invoice not found"));
    }
}
