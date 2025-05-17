package com.crashero.core.service;

import com.crashero.core.port.ProductConfigurationPort;
import com.crashero.core.port.ProductPort;
import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import com.crashero.model.ProductConfiguration;
import com.crashero.model.exception.ProductException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductService {
    private final ProductPort productPort;
    private final ProductConfigurationPort productConfigurationPort;

    public ProductService(ProductPort productPort, ProductConfigurationPort productConfigurationPort) {
        this.productPort = productPort;
        this.productConfigurationPort = productConfigurationPort;
    }

    public List<ProductConfiguration> getProductConfiguration(Long id) {
        Product product = getProductById(id);
        return product.getConfiguration();
    }

    public Product createProduct(Product product) {
        return productPort.save(product);
    }

    public Product getProductById(Long id) {
        return productPort.findById(id).orElseThrow(() -> new ProductException("Product not found"));
    }

    public PageableContentDTO<Product> getAllProducts(Pageable pageable) {
        Page<Product> productsPage = productPort.findAll(pageable);
        List<Product> products = productsPage.getContent();

        return PageableContentDTO.from(productsPage, products);
    }

    public void deleteProduct(Long id) {
        productPort.deleteById(getProductById(id).getId());
    }

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
