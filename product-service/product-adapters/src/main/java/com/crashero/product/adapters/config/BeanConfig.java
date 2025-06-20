package com.crashero.product.adapters.config;

import com.crashero.core.port.ProductConfigurationPort;
import com.crashero.core.port.ProductPort;
import com.crashero.core.service.ProductService;
import com.crashero.core.service.impl.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public ProductServiceImpl productService(ProductPort productPort, ProductConfigurationPort productConfigurationPort) {
        return new ProductServiceImpl(productPort, productConfigurationPort);
    }
}
