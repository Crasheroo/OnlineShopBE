package com.crashero.user.adapters.out;

import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import com.crashero.user.config.FeignConfig;
import com.crashero.user.config.ProductClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-client", url = "${product.service.url}", configuration = FeignConfig.class, fallback = ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("/products")
    PageableContentDTO<Product> getProducts(Pageable pageable);

    @GetMapping("/products/{id}/configuration")
    Object getProductConfiguration(@PathVariable("id") Long id);

    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable("id") Long id);
}
