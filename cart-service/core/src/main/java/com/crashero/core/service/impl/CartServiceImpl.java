package com.crashero.core.service.impl;

import com.crashero.core.service.CartPort;
import com.crashero.core.service.CartService;
import com.crashero.model.AddProductToCart;
import com.crashero.model.Cart;
import com.crashero.model.CartItem;
import com.crashero.model.exception.CartException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartPort cartPort;

    @Override
    public Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItems(new ArrayList<>());
        return cartPort.save(cart);
    }

    @Override
    public Cart getCart(Long cartId) {
        return cartPort.findById(cartId)
                .orElseThrow(() -> new CartException("Cart not found"));
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartPort.findByUserId(userId)
                .orElseThrow(() -> new CartException("Cart not found"));
    }

    @Override
    public void deleteCart(Long cartId) {
        cartPort.deleteById(cartId);
    }

    @Override
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
                        .price(command.getPrice() + command.getAdditionalPrice())
                        .selectedConfigurations(command.getSelectedConfigurations())
                        .additionalPrice(command.getAdditionalPrice())
                        .build())
        );

        cart.setItems(items);
        cartPort.save(cart);
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartPort.findAll();
    }
}
