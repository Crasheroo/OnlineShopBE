package com.crashero.cart.adapters.in.web;

import com.crashero.core.service.CartService;
import com.crashero.model.AddProductToCart;
import com.crashero.model.Cart;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    @Operation(summary = "Get cart by cartId")
    @ApiResponse(responseCode = "200", description = "Cart found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Cart.class))})
    @GetMapping("/{cartId}")
    public Cart getCart(@PathVariable("cartId") Long cartId) {
        log.info("Get cart by cartId: {}", cartId);
        return cartService.getCart(cartId);
    }

    @Operation(summary = "Get user's cart by userId")
    @ApiResponse(responseCode = "200", description = "Cart found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Cart.class))})
    @GetMapping("/user/{userId}")
    public Cart getCartByUserId(@PathVariable("userId") Long userId) {
        log.info("Get cart by userId: {}", userId);
        return cartService.getCartByUserId(userId);
    }

    @Operation(summary = "Delete cart by cartId")
    @ApiResponse(responseCode = "200", description = "Cart found and deleted")
    @DeleteMapping("/{cartId}")
    public void delete(@PathVariable("cartId") Long cartId) {
        log.info("Delete cart by cartId: {}", cartId);
        cartService.deleteCart(cartId);
    }

    @Operation(summary = "Add product to cart by userId, productId and product quantity and create cart when not found")
    @ApiResponse(responseCode = "200", description = "product and user found, product added")
    @PostMapping("/add-product")
    public void addProductToCartAndCreateCartWhenNotFound(@RequestBody AddProductToCart command) {
        log.info("AddProductToCart command: {}", command);
        cartService.addProductToCart(command);
    }

    @Operation(summary = "Get all carts")
    @ApiResponse(responseCode = "200", description = "Carts found")
    @GetMapping
    public List<Cart> getCarts() {
        log.info("Get carts");
        return cartService.getAllCarts();
    }
}
