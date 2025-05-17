package com.crashero.core.port;

import com.crashero.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductPort {
    Product save(Product product);
    Optional<Product> findById(Long id);
    void deleteById(Long id);
    Page<Product> findAll(Pageable pageable);
}
