package com.crashero.user.adapters.in;

import com.crashero.model.*;
import com.crashero.user.core.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ui")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "Products found")
    @GetMapping("/products")
    public PageableContentDTO<Product> browseProducts() {
        return userService.browseProducts();
    }

    @Operation(summary = "Add product to cart and create the cart if not found")
    @ApiResponse(responseCode = "200", description = "Product found and added")
    @PostMapping("/carts/add-product")
    public void addToCart(@RequestBody AddProductToCartCommand command) {
        userService.addToCart(command);
    }

    @Operation(summary = "Checkout cart by its id and usersId")
    @ApiResponse(responseCode = "200", description = "Cart and user found, cart finalized and invoice created",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Order.class))})
    @PostMapping("/checkout/{cartId}/{userId}")
    public Order checkout(@PathVariable("cartId") Long cartId, @PathVariable("userId") Long userId) {
        return userService.checkout(cartId, userId);
    }

    @Operation(summary = "Get order history by userId")
    @ApiResponse(responseCode = "200", description = "User found and orders are displayed")
    @GetMapping("/orders/user/{userId}")
    public List<Order> getOrderHistory(@PathVariable("userId") Long userId) {
        return userService.getOrderHistory(userId);
    }

    @Operation(summary = "Get cart by its id")
    @ApiResponse(responseCode = "200", description = "Cart found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Cart.class))})
    @GetMapping("/carts/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return userService.getCartById(id);
    }

    @Operation(summary = "Get product configuration by its id")
    @ApiResponse(responseCode = "200", description = "Product found and its type is correct")
    @GetMapping("/products/{id}/configuration")
    public Object getProductConfiguration(@PathVariable("id") Long id) {
        return userService.getProductConfiguration(id);
    }

    @Operation(summary = "Get product by its id")
    @ApiResponse(responseCode = "200", description = "Product found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))})
    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return userService.getProductById(id);
    }

    @Operation(summary = "Get invoice by userId")
    @ApiResponse(responseCode = "200", description = "User found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Invoice.class))})
    @GetMapping("/orders/invoice/{userId}")
    public List<Invoice> getInvoiceByUserId(@PathVariable("userId") Long userId) {
        return userService.getInvoicesByUserId(userId);
    }

}
