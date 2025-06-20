package com.crashero.user.adapters.out;

import com.crashero.model.AddProductToCart;
import com.crashero.model.Cart;
import com.crashero.user.adapters.config.FeignConfig;
import com.crashero.user.core.port.out.CartPort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "cart-client", url = "${cart.service.url}", configuration = FeignConfig.class)
public interface CartClient extends CartPort {

    @PostMapping("/carts/add-product")
    void addProductToCart(AddProductToCart command);

    @GetMapping("/carts/user/{userId}")
    Cart getCart(@PathVariable("userId") Long userId);

    @DeleteMapping("/carts/{cartId}")
    void deleteCart(@PathVariable("cartId") Long cartId);

    @GetMapping("/carts/user/{userId}")
    Optional<Cart> findCartByUserId(@PathVariable("userId") Long userId);

    @PostMapping("/carts/{userId}/create")
    Cart createCart(@PathVariable("userId") Long userId);

    @GetMapping("/carts")
    List<Cart> getCarts();
}
