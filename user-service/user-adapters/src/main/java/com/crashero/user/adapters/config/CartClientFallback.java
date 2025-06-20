package com.crashero.user.adapters.config;

import com.crashero.model.AddProductToCart;
import com.crashero.model.Cart;
import com.crashero.user.adapters.out.CartClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CartClientFallback implements CartClient {

    @Override
    public void addProductToCart(AddProductToCart command) {
        log.error("Falling back to addProductToCart");
        throw new RuntimeException("Service error");
    }

    @Override
    public Cart getCart(Long cartId) {
        log.error("Falling back to getCart");
        throw new RuntimeException("Falling back to getCart");
    }

    @Override
    public void deleteCart(Long cartId) {
        log.error("Falling back to deleteCart");
        throw new RuntimeException("Falling back to deleteCart");
    }

    @Override
    public Optional<Cart> findCartByUserId(Long userId) {
        log.error("Falling back to findCartByUserId");
        throw new RuntimeException("Falling back to findCartByUserId");
    }

    @Override
    public Cart createCart(Long userId) {
        log.error("Falling back to createCart");
        throw new RuntimeException("Falling back to createCart");
    }

    @Override
    public List<Cart> getCarts() {
        log.error("Falling back to getCarts");
        throw new RuntimeException("Falling back to getCarts");
    }
}
