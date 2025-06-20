package com.crashero.core.service;

import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import com.crashero.model.ProductConfiguration;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductConfiguration> getProductConfiguration(Long id);

    Product createProduct(Product product);

    Product getProductById(Long id);

    PageableContentDTO<Product> getAllProducts(Pageable pageable);

    void deleteProduct(Long id);

    Product updateProduct(Long productId, Product updatedData);
}
