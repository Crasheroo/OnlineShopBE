package com.crashero.user.adapters.in;

import com.crashero.model.*;
import com.crashero.user.core.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ui")
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "Products found")
    @GetMapping("/products")
    public PageableContentDTO<Product> browseProducts(@ParameterObject Pageable pageable) {
        log.info("Received request to browse products. Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return userService.browseProducts(pageable);
    }

    @Operation(summary = "Add product to cart and create the cart if not found")
    @ApiResponse(responseCode = "200", description = "Product found and added")
    @PostMapping("/carts/add-product")
    public void addToCart(@RequestBody AddProductToCartCommand command) {
        log.info("Received request to add product to cart. Command: {}", command);
        userService.addToCart(command);
    }

    @Operation(summary = "Checkout cart by its id and usersId")
    @ApiResponse(responseCode = "200", description = "Cart and user found, cart finalized and invoice created",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Order.class))})
    @PostMapping("/checkout/{cartId}/{userId}")
    public Order checkout(@PathVariable("cartId") Long cartId, @PathVariable("userId") Long userId) {
        log.info("Checkout requested for cartId={} and userId={}", cartId, userId);
        return userService.checkout(cartId, userId);
    }

    @Operation(summary = "Get order history by userId")
    @ApiResponse(responseCode = "200", description = "User found and orders are displayed")
    @GetMapping("/orders/user/{userId}")
    public List<Order> getOrderHistory(@PathVariable("userId") Long userId) {
        log.info("Received request to retrieve order history for userId={}", userId);
        return userService.getOrderHistory(userId);
    }

    @Operation(summary = "Get cart by its userId")
    @ApiResponse(responseCode = "200", description = "Cart found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Cart.class))})
    @GetMapping("/carts/user/{userId}")
    public Cart getCartByUserId(@PathVariable("userId") Long userId) {
        log.info("Received request to get cart for userId={}", userId);
        return userService.getCartByUserId(userId);
    }

    @Operation(summary = "Get product configuration by its id")
    @ApiResponse(responseCode = "200", description = "Product found and its type is correct")
    @GetMapping("/products/{id}/configuration")
    public Object getProductConfiguration(@PathVariable("id") Long id) {
        log.info("Received request to retrieve product configuration for id={}", id);
        return userService.getProductConfiguration(id);
    }

    @Operation(summary = "Get product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "500", description = "Product not found")
    })
    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        log.info("Received request to retrieve product for id={}", id);
        return userService.getProductById(id);
    }

    @Operation(summary = "Get invoice by userId")
    @ApiResponse(responseCode = "200", description = "User found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Invoice.class))})
    @GetMapping("/orders/invoice/{userId}")
    public List<Invoice> getInvoiceByUserId(@PathVariable("userId") Long userId) {
        log.info("Received request to retrieve invoice for userId={}", userId);
        return userService.getInvoicesByUserId(userId);
    }
}
