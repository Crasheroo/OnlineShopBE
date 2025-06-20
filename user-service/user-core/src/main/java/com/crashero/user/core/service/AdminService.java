package com.crashero.user.core.service;

import com.crashero.model.Cart;
import com.crashero.model.CreateProductCommand;
import com.crashero.model.Product;

import java.util.List;

public interface AdminService {
    List<Cart> getCarts();

    Product createProduct(CreateProductCommand command);

    Product updateProduct(Long productId, Product updatedProduct);

    void deleteProduct(Long productId);
}
