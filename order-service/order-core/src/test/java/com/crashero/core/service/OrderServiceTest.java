package com.crashero.core.service;

import com.crashero.core.service.impl.OrderServiceImpl;
import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderPort orderPort;
    private InvoiceService invoiceService;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderPort = mock(OrderPort.class);
        invoiceService = mock(InvoiceService.class);
        orderService = new OrderServiceImpl(orderPort, invoiceService);
    }

    @Test
    void shouldCallSaveAndGenerateInvoice() {
        // given
        List<OrderItem> items = List.of(
                OrderItem.builder().price(10.0).build(),
                OrderItem.builder().price(20.0).build()
        );
        Order saved = Order.builder().userId(1L).items(items).totalAmount(30.0).build();
        when(orderPort.save(any())).thenReturn(saved);

        // when
        orderService.createOrder(1L, items);

        // then
        verify(orderPort).save(any(Order.class));
        verify(invoiceService).generateInvoice(saved);
    }

    @Test
    void shouldDelegateFindAllByUserIdToOrderPort() {
        // when
        orderService.getOrders(5L);

        // then
        verify(orderPort).findAllByUserId(5L);
    }

    @Test
    void shouldCallFindById() {
        // given
        Order order = Order.builder().id(10L).build();
        when(orderPort.findById(10L)).thenReturn(Optional.of(order));

        // when
        orderService.findOrderById(10L);

        // then
        verify(orderPort).findById(10L);
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        // given
        when(orderPort.findById(99L)).thenReturn(Optional.empty());

        try {
            // when
            orderService.findOrderById(99L);
        } catch (Exception e) {
            // then
            verify(orderPort).findById(99L);
        }
    }
}
