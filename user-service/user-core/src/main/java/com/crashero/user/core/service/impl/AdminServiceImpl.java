package com.crashero.user.core.service.impl;

import com.crashero.model.Cart;
import com.crashero.model.CreateProductCommand;
import com.crashero.model.Product;
import com.crashero.user.core.service.AdminService;
import com.crashero.user.core.port.out.CartPort;
import com.crashero.user.core.port.out.ProductPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ProductPort productPort;
    private final CartPort cartPort;

    @Override
    public List<Cart> getCarts() {
        return cartPort.getCarts();
    }

    @Override
    public Product createProduct(CreateProductCommand command) {
        return productPort.createProduct(command);
    }

    @Override
    public Product updateProduct(Long productId, Product updatedProduct) {
        return productPort.updateProduct(productId, updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        productPort.deleteProduct(productId);
    }
}
