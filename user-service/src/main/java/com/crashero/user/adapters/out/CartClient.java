package com.crashero.user.adapters.out;

import com.crashero.model.AddProductToCart;
import com.crashero.model.Cart;
import com.crashero.user.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "cart-client", url = "${cart.service.url}", configuration = FeignConfig.class)
public interface CartClient {

    @PostMapping("/carts/add-product")
    void addProductToCart(AddProductToCart command);

    @GetMapping("/carts/{cartId}")
    Cart getCart(@PathVariable("cartId") Long cartId);

    @DeleteMapping("/carts/{cartId}")
    void deleteCart(@PathVariable("cartId") Long cartId);

    @GetMapping("/carts/user/{userId}")
    Optional<Cart> findCartByUserId(@PathVariable("userId") Long userId);

    @PostMapping("/carts/{userId}/create")
    Cart createCart(@PathVariable("userId") Long userId);
}
