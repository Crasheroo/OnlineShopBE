package com.crashero.core.service.impl;

import com.crashero.core.port.ProductPort;
import com.crashero.core.service.ProductService;
import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import com.crashero.model.ProductConfiguration;
import com.crashero.model.exception.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductPort productPort;

    @Override
    public List<ProductConfiguration> getProductConfiguration(Long id) {
        Product product = getProductById(id);
        return product.getConfiguration();
    }

    @Override
    public Product createProduct(Product product) {
        return productPort.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productPort.findById(id).orElseThrow(() -> new ProductException("Product not found"));
    }

    @Override
    public PageableContentDTO<Product> getAllProducts(Pageable pageable) {
        Page<Product> productsPage = productPort.findAll(pageable);
        List<Product> products = productsPage.getContent();

        return PageableContentDTO.from(productsPage, products);
    }

    @Override
    public void deleteProduct(Long id) {
        productPort.deleteById(getProductById(id).getId());
    }

    @Override
    public Product updateProduct(Long productId, Product updatedData) {
        Product product = productPort.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found"));

        product.setProductName(updatedData.getProductName());
        product.setPrice(updatedData.getPrice());
        product.setType(updatedData.getType());

        productPort.save(product);
        return product;
    }
}
