package com.crashero.core.service;

import com.crashero.model.Invoice;
import com.crashero.model.Order;

import java.util.List;

public interface InvoiceService {
    void generateInvoice(Order order);

    List<Invoice> getInvoicesByUserId(Long userId);

    Invoice getInvoiceById(Long id);
}
