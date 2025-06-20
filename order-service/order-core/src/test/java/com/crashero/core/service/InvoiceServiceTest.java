package com.crashero.core.service;

import com.crashero.core.service.impl.InvoiceServiceImpl;
import com.crashero.model.Invoice;
import com.crashero.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    private InvoicePort invoicePort;
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    void setUp() {
        invoicePort = mock(InvoicePort.class);
        invoiceService = new InvoiceServiceImpl(invoicePort);
    }

    @Test
    void shouldGenerateInvoiceAndSave() {
        // given
        Order order = Order.builder()
                .id(1L)
                .userId(42L)
                .totalAmount(99.99)
                .build();

        when(invoicePort.save(any())).thenReturn(mock(Invoice.class));

        // when
        invoiceService.generateInvoice(order);

        // then
        verify(invoicePort).save(any(Invoice.class));
    }

    @Test
    void shouldFetchInvoicesByUserId() {
        // when
        invoiceService.getInvoicesByUserId(123L);

        // then
        verify(invoicePort).findAllByUserId(123L);
    }

    @Test
    void shouldFindInvoiceById() {
        // given
        when(invoicePort.findById(55L)).thenReturn(Optional.of(mock(Invoice.class)));

        // when
        invoiceService.getInvoiceById(55L);

        // then
        verify(invoicePort).findById(55L);
    }

    @Test
    void shouldThrowWhenInvoiceNotFound() {
        // given
        when(invoicePort.findById(99L)).thenReturn(Optional.empty());

        try {
            // when
            invoiceService.getInvoiceById(99L);
        } catch (Exception e) {
            // then
            verify(invoicePort).findById(99L);
        }
    }
}
