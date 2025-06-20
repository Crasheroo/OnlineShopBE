package com.crashero.core.service;

import com.crashero.model.AddProductToCart;
import com.crashero.model.Cart;

import java.util.List;

public interface CartService {
    Cart createCart(Long userId);

    Cart getCart(Long cartId);

    Cart getCartByUserId(Long userId);

    void deleteCart(Long cartId);

    void addProductToCart(AddProductToCart command);

    List<Cart> getAllCarts();
}
