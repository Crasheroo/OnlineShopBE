package com.crashero.order.adapters.config;

import com.crashero.core.service.InvoicePort;
import com.crashero.core.service.InvoiceService;
import com.crashero.core.service.OrderPort;
import com.crashero.core.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public OrderService orderService(OrderPort orderPort, InvoiceService invoiceService) {
        return new OrderService(orderPort, invoiceService);
    }

    @Bean
    public InvoiceService invoiceService(InvoicePort invoicePort) {
        return new InvoiceService(invoicePort);
    }
}
