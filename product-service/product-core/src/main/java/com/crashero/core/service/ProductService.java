package com.crashero.core.service;

import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import com.crashero.model.ProductType;
import com.crashero.model.configuration.ComputerConfiguration;
import com.crashero.model.configuration.SmartphoneConfiguration;
import com.crashero.model.exception.ProductException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductService {
    private final ProductPort productPort;

    public ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    public Object getProductConfiguration(Long id) {
        Product product = getProductById(id);

        if (product.getType() == ProductType.COMPUTER) {
            return ComputerConfiguration.builder()
                    .availableProcessors(List.of("Intel i5", "Intel i7", "AMD Ryzen 5", "AMD Ryzen 7"))
                    .availableRamOptions(List.of(8, 16, 32))
                    .build();
        } else if (product.getType() == ProductType.SMARTPHONE) {
            return SmartphoneConfiguration.builder()
                    .availableColors(List.of("Black", "White", "Blue"))
                    .batteryCapacities(List.of(3000, 4000, 5000))
                    .availableAccessories(List.of("Earphones", "Case", "Screen Protector"))
                    .build();
        } else {
            throw new ProductException("Product of type ELECTRONICS does not have configurable options.");
        }
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
