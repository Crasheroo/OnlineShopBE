package com.crashero.core.service;

import com.crashero.common.exception.CartException;
import com.crashero.model.AddProductToCart;
import com.crashero.model.Cart;
import com.crashero.model.CartItem;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartService {
    private final CartPort cartPort;

    public CartService(CartPort cartPort) {
        this.cartPort = cartPort;
    }

    private Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItems(new ArrayList<>());
        return cartPort.save(cart);
    }

    public Cart getCart(Long cartId) {
        return cartPort.findById(cartId)
                .orElseThrow(() -> new CartException("Cart not found", HttpStatus.NOT_FOUND));
    }

    public Cart getCartByUserId(Long userId) {
        return cartPort.findByUserId(userId)
                .orElseThrow(() -> new CartException("Cart not found", HttpStatus.NOT_FOUND));
    }

    public void deleteCart(Long cartId) {
        cartPort.deleteById(cartId);
    }

    public void addProductToCart(AddProductToCart command) {
        Cart cart = cartPort.findByUserId(command.getUserId())
                .orElseGet(() -> createCart(command.getUserId()));

        List<CartItem> items = new ArrayList<>(cart.getItems());

        Optional<CartItem> existingItemOpt = items.stream()
                .filter(item -> item.getProductId().equals(command.getProductId()))
                .findFirst();

        existingItemOpt.ifPresentOrElse(
                item -> item.setQuantity(item.getQuantity() + command.getQuantity()),
                () -> items.add(CartItem.builder()
                        .productId(command.getProductId())
                        .productName(command.getProductName())
                        .quantity(command.getQuantity())
                        .price(command.getPrice())
                        .build())
        );

        cart.setItems(items);
        cartPort.save(cart);
    }

    public List<Cart> getAllCarts() {
        return cartPort.findAll();
    }
}
