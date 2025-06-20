package com.crashero.order.adapters.config;

import com.crashero.core.service.InvoicePort;
import com.crashero.core.service.InvoiceService;
import com.crashero.core.service.OrderPort;
import com.crashero.core.service.impl.InvoiceServiceImpl;
import com.crashero.core.service.impl.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public OrderServiceImpl orderService(OrderPort orderPort, InvoiceService invoiceService) {
        return new OrderServiceImpl(orderPort, invoiceService);
    }

    @Bean
    public InvoiceServiceImpl invoiceService(InvoicePort invoicePort) {
        return new InvoiceServiceImpl(invoicePort);
    }
}
