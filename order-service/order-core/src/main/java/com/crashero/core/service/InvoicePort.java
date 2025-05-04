package com.crashero.core.service;

import com.crashero.model.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoicePort {
    Invoice save(Invoice invoice);
    Optional<Invoice> findById(Long id);
    List<Invoice> findAllByUserId(Long userId);
}
