package com.crashero.user.core.service;

import com.crashero.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    PageableContentDTO<Product> browseProducts(Pageable pageable);

    void addToCart(AddProductToCartCommand command);

    Order checkout(Long cartId, Long userId);

    List<Order> getOrderHistory(Long userId);

    Cart getCartByUserId(Long userId);

    Object getProductConfiguration(Long id);

    Product getProductById(Long productId);

    List<Invoice> getInvoicesByUserId(Long userId);
}