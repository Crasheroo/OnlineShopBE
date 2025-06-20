package com.crashero.user.adapters.out;

import com.crashero.model.CreateProductCommand;
import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import com.crashero.user.adapters.config.FeignConfig;
import com.crashero.user.adapters.config.fallback.ProductClientFallback;
import com.crashero.user.core.port.out.ProductPort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-client", url = "${product.service.url}", configuration = FeignConfig.class, fallback = ProductClientFallback.class)
public interface ProductClient extends ProductPort {

    @GetMapping("/products")
    PageableContentDTO<Product> getProducts(Pageable pageable);

    @GetMapping("/products/{id}/configuration")
    Object getProductConfiguration(@PathVariable("id") Long id);

    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable("id") Long id);

    @PostMapping("/products")
    Product createProduct(@RequestBody CreateProductCommand command);

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable("id") Long id);

    @PatchMapping("/products/{productId}/update")
    Product updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product);
}
