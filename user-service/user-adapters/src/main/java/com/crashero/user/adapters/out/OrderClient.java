package com.crashero.user.adapters.out;

import com.crashero.model.Invoice;
import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import com.crashero.user.adapters.config.FeignConfig;
import com.crashero.user.adapters.config.OrderClientFallback;
import com.crashero.user.core.port.out.OrderPort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-client", url = "${order.service.url}", configuration = FeignConfig.class, fallback = OrderClientFallback.class)
public interface OrderClient extends OrderPort {

    @GetMapping("/orders/invoice/{userId}")
    List<Invoice> getInvoicesByUserId(@PathVariable("userId") Long userId);

    @PostMapping("/orders/{userId}")
    Order createOrder(@PathVariable("userId") Long userId, @RequestBody List<OrderItem> items);

    @GetMapping("/orders/user/{userId}")
    List<Order> getOrdersByUserId(@PathVariable("userId") Long userId);

}
