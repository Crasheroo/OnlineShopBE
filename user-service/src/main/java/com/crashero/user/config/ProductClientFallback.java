package com.crashero.user.config;

import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import com.crashero.user.adapters.out.ProductClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {
    private static final Logger log = LoggerFactory.getLogger(ProductClientFallback.class);

    @Override
    public PageableContentDTO<Product> getProducts() {
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
}
