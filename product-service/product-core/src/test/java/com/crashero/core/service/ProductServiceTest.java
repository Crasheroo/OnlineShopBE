package com.crashero.core.service;

import com.crashero.core.port.ProductPort;
import com.crashero.core.service.impl.ProductServiceImpl;
import com.crashero.model.Product;
import com.crashero.model.ProductType;
import com.crashero.model.exception.ProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    private ProductServiceImpl productService;
    private ProductPort productPort;

    @BeforeEach
    public void setUp() {
        productPort = mock(ProductPort.class);
        productService = new ProductServiceImpl(productPort);
    }

    @Test
    void shouldThrowOnInvalidProductType() {
        Product invalid = Product.builder().id(3L).type(ProductType.ELECTRONICS).build();
        when(productPort.findById(3L)).thenReturn(Optional.of(invalid));

        ProductException exception = assertThrows(ProductException.class, () -> productService.getProductConfiguration(3L));

        assertEquals("Product of type ELECTRONICS does not have configurable options.", exception.getMessage());
    }

    @Test
    void shouldCreateProduct() {
        Product product = Product.builder().id(1L).productName("Phone").build();
        when(productPort.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);

        assertEquals(product, result);
    }

    @Test
    void shouldReturnProductById() {
        Product product = Product.builder().id(1L).build();
        when(productPort.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals(product, result);
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        when(productPort.findById(99L)).thenReturn(Optional.empty());

        ProductException exception = assertThrows(ProductException.class, () -> productService.getProductById(99L));

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void shouldReturnAllProductsAsPageableContent() {
        Product product = Product.builder().id(1L).build();
        Page<Product> page = new PageImpl<>(List.of(product), PageRequest.of(0, 10), 1);
        when(productPort.findAll(PageRequest.of(0, 10))).thenReturn(page);
    }

    @Test
    void shouldDeleteProductById() {
        Product product = Product.builder().id(5L).build();
        when(productPort.findById(5L)).thenReturn(Optional.of(product));

        productService.deleteProduct(5L);

        verify(productPort).deleteById(5L);
    }

    @Test
    void shouldUpdateProduct() {
        Product existing = Product.builder().id(1L).productName("Old").price(100.0).type(ProductType.ELECTRONICS).build();
        Product updated = Product.builder().productName("New").price(200.0).type(ProductType.SMARTPHONE).build();
        when(productPort.findById(1L)).thenReturn(Optional.of(existing));

        Product result = productService.updateProduct(1L, updated);

        assertEquals("New", result.getProductName());
        assertEquals(200.0, result.getPrice());
        assertEquals(ProductType.SMARTPHONE, result.getType());
        verify(productPort).save(existing);
    }
}
