package com.crashero.core.service;

import com.crashero.common.exception.OrderException;
import com.crashero.model.Invoice;
import com.crashero.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class InvoiceService {
    private final InvoicePort invoicePort;

    public Invoice generateInvoice(Order order) {
        Invoice build = Invoice.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .amount(order.getTotalAmount())
                .issuedAt(LocalDateTime.now())
                .build();

        return invoicePort.save(build);
    }

    public List<Invoice> getInvoicesByUserId(Long userId) {
        return invoicePort.findAllByUserId(userId);
    }

    public Invoice getInvoiceById(Long id) {
        return invoicePort.findById(id)
                .orElseThrow(() -> new OrderException("Invoice not found", HttpStatus.NOT_FOUND));
    }
}
