package com.crashero.user.core.port.out;

import com.crashero.model.CreateProductCommand;
import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import org.springframework.data.domain.Pageable;

public interface ProductPort {
    PageableContentDTO<Product> getProducts(Pageable pageable);
    Object getProductConfiguration(Long id);
    Product getProductById(Long id);
    Product createProduct(CreateProductCommand command);
    void deleteProduct(Long id);
    Product updateProduct(Long productId, Product product);
}
