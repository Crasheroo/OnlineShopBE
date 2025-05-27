package com.crashero.order.adapters.in.web;

import com.crashero.core.service.InvoiceService;
import com.crashero.core.service.OrderService;
import com.crashero.model.Invoice;
import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import com.crashero.model.exception.OrderException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private InvoiceService invoiceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOrder() throws Exception {
        Long userId = 1L;
        List<OrderItem> items = Collections.emptyList();
        Order mockOrder = new Order();
        mockOrder.setId(100L);

        Mockito.when(orderService.createOrder(eq(userId), any())).thenReturn(mockOrder);

        mockMvc.perform(post("/orders/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(items)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L));
    }

    @Test
    void testGetOrdersByUserId() throws Exception {
        Long userId = 1L;
        Order mockOrder = new Order();
        mockOrder.setUserId(userId);

        Mockito.when(orderService.getOrders(userId)).thenReturn(List.of(mockOrder));

        mockMvc.perform(get("/orders/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userId));
    }

    @Test
    void testGetOrderById() throws Exception {
        Order mockOrder = new Order();
        mockOrder.setId(200L);

        Mockito.when(orderService.findOrderById(200L)).thenReturn(mockOrder);

        mockMvc.perform(get("/orders/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(200L));
    }

    @Test
    void testGetInvoicesByUserId() throws Exception {
        Invoice mockInvoice = new Invoice();
        mockInvoice.setUserId(10L);

        Mockito.when(invoiceService.getInvoicesByUserId(10L)).thenReturn(List.of(mockInvoice));

        mockMvc.perform(get("/orders/invoice/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(10L));
    }

    @Test
    void testGetInvoiceByUserId() throws Exception {
        Invoice mockInvoice = new Invoice();
        mockInvoice.setUserId(10L);

        Mockito.when(invoiceService.getInvoiceById(10L)).thenReturn(mockInvoice);

        mockMvc.perform(get("/orders/invoice/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(10L));
    }

    @Test
    void testGetOrdersByUserId_NotFound_ReturnsEmptyList() throws Exception {
        Long userId = 999L;

        Mockito.when(orderService.getOrders(userId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/orders/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetOrderById_NotFound_Returns404() throws Exception {
        Long orderId = 999L;

        Mockito.when(orderService.findOrderById(orderId))
                .thenThrow(new OrderException("Order not found"));

        mockMvc.perform(get("/orders/{id}", orderId))
                .andExpect(status().isConflict());
    }

    @Test
    void testGetInvoicesByUserId_EmptyResult_ReturnsEmptyList() throws Exception {
        Long userId = 999L;

        Mockito.when(invoiceService.getInvoicesByUserId(userId))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/orders/invoice/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetInvoiceByUserId_NotFound_Returns404() throws Exception {
        Long userId = 999L;

        Mockito.when(invoiceService.getInvoiceById(userId))
                .thenThrow(new OrderException("Invoice not found"));

        mockMvc.perform(get("/orders/invoice/user/{userId}", userId))
                .andExpect(status().isConflict());
    }
}
