package com.crashero.core.port;

import com.crashero.model.ProductConfiguration;

import java.util.List;
import java.util.Optional;

public interface ProductConfigurationPort {
    ProductConfiguration save(ProductConfiguration productConfiguration);
    Optional<ProductConfiguration> findById(Long id);
    void deleteById(Long id);
    List<ProductConfiguration> findAll();
}
