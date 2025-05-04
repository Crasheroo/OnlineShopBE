package com.crashero.product.adapters.config;

import com.crashero.core.service.ProductPort;
import com.crashero.core.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public ProductService productService(ProductPort productPort) {
        return new ProductService(productPort);
    }
}
