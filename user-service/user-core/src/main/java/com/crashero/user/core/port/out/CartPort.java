package com.crashero.user.core.port.out;

import com.crashero.model.AddProductToCart;
import com.crashero.model.Cart;

import java.util.List;

public interface CartPort {
    void addProductToCart(AddProductToCart command);
    Cart getCart(Long userId);
    void deleteCart(Long cartId);
    List<Cart> getCarts();
}
