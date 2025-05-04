package com.crashero.user.adapters.out;

import com.crashero.model.Invoice;
import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import com.crashero.user.config.FeignConfig;
import com.crashero.user.config.OrderClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-client", url = "${order.service.url}", configuration = FeignConfig.class, fallback = OrderClientFallback.class)
public interface OrderClient {

    @GetMapping("/orders/invoice/{userId}")
    List<Invoice> getInvoicesByUserId(@PathVariable("userId") Long userId);

    @PostMapping("/orders/{userId}")
    Order createOrder(@PathVariable Long userId, @RequestBody List<OrderItem> items);

    @GetMapping("/orders/user/{userId}")
    List<Order> getOrdersByUserId(@PathVariable("userId") Long userId);

}
