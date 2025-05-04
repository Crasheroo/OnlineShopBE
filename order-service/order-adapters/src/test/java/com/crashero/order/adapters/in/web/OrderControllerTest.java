package com.crashero.order.adapters.in.web;

import com.crashero.core.service.InvoiceService;
import com.crashero.core.service.OrderService;
import com.crashero.model.Invoice;
import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private InvoiceService invoiceService;

    private ObjectMapper objectMapper;
    private Order sampleOrder;
    private Invoice sampleInvoice;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        sampleOrder = new Order();
        sampleOrder.setId(1L);
        sampleOrder.setUserId(10L);

        sampleInvoice = new Invoice();
        sampleInvoice.setId(1L);
        sampleInvoice.setUserId(10L);
    }

    @Test
    void shouldCreateOrder() throws Exception {
        List<OrderItem> items = List.of(new OrderItem(1L, "Test Product", 2, 99.99));
        Mockito.when(orderService.createOrder(eq(10L), any())).thenReturn(sampleOrder);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(items)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldGetOrdersForUser() throws Exception {
        Mockito.when(orderService.getOrders(10L)).thenReturn(List.of(sampleOrder));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void shouldGetOrderById() throws Exception {
        Mockito.when(orderService.findOrderById(1L)).thenReturn(sampleOrder);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldGetInvoicesByUserId() throws Exception {
        Mockito.when(invoiceService.getInvoicesByUserId(10L)).thenReturn(List.of(sampleInvoice));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/invoice/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void shouldGetSingleInvoiceByUserId() throws Exception {
        Mockito.when(invoiceService.getInvoiceById(10L)).thenReturn(sampleInvoice);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/invoice/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
