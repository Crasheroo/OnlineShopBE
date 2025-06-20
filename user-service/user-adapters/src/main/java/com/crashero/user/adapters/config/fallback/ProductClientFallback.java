package com.crashero.user.adapters.config.fallback;

import com.crashero.model.CreateProductCommand;
import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import com.crashero.user.adapters.out.ProductClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {
    private static final Logger log = LoggerFactory.getLogger(ProductClientFallback.class);

    @Override
    public PageableContentDTO<Product> getProducts(Pageable pageable) {
        log.error("getProducts called");
        throw new RuntimeException("Falling back to getProducts");
    }

    @Override
    public Object getProductConfiguration(Long id) {
        log.error("getProductConfiguration called");
        throw new RuntimeException("Falling back to getProductConfiguration");
    }

    @Override
    public Product getProductById(Long id) {
        log.error("getProductById called");
        throw new RuntimeException("Falling back to getProductById");
    }

    @Override
    public Product createProduct(CreateProductCommand command) {
        log.error("createProduct called");
        throw new RuntimeException("Falling back to createProduct");
    }

    @Override
    public void deleteProduct(Long id) {
        log.error("deleteProduct called");
        throw new RuntimeException("Falling back to deleteProduct");
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        log.error("updateProduct called");
        throw new RuntimeException("Falling back to updateProduct");
    }
}
