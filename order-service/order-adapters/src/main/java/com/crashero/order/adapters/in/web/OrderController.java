package com.crashero.order.adapters.in.web;

import com.crashero.core.service.InvoiceService;
import com.crashero.core.service.OrderService;
import com.crashero.model.Invoice;
import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final InvoiceService invoiceService;

    @Operation(summary = "Create order by userId and OrderItems")
    @ApiResponse(responseCode = "200", description = "Order created",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Order.class))})
    @PostMapping("/{userId}")
    public Order createOrder(@PathVariable("userId") Long userId, @RequestBody List<OrderItem> items) {
        return orderService.createOrder(userId, items);
    }

    @Operation(summary = "Get orders by userId")
    @ApiResponse(responseCode = "200", description = "Orders found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Order.class))})
    @GetMapping("/user/{userId}")
    public List<Order> getOrders(@PathVariable("userId") Long userId) {
        return orderService.getOrders(userId);
    }

    @Operation(summary = "Get order by it's id")
    @ApiResponse(responseCode = "200", description = "Order found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Order.class))})
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable("id") Long id) {
        return orderService.findOrderById(id);
    }

    @Operation(summary = "Get invoices by userId")
    @ApiResponse(responseCode = "200", description = "User found, invoices found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Invoice.class))})
    @GetMapping("/invoice/{userId}")
    public List<Invoice> getInvoicesByUserId(@PathVariable("userId") Long userId) {
        return invoiceService.getInvoicesByUserId(userId);
    }

    @Operation(summary = "Get invoice by userId")
    @ApiResponse(responseCode = "200", description = "User found, invoice found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Invoice.class))})
    @GetMapping("/invoice/user/{userId}")
    public Invoice getInvoiceByUserId(@PathVariable("userId") Long userId) {
        return invoiceService.getInvoiceById(userId);
    }
}
