package com.crashero.product.adapters.in.web;

import com.crashero.core.service.ProductService;
import com.crashero.model.Product;
import com.crashero.model.ProductType;
import com.crashero.model.configuration.CreateProductCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduct() throws Exception {
        CreateProductCommand command = new CreateProductCommand();
        Product createdProduct = Product.builder()
                .id(1L)
                .productName("TV")
                .price(999.99)
                .type(ProductType.ELECTRONICS)
                .build();

        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productName").value("TV"));
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = Product.builder().id(1L).productName("Laptop").build();
        Mockito.when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Laptop"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetProductConfiguration() throws Exception {
        Object config = new Object();
        Mockito.when(productService.getProductConfiguration(1L)).thenReturn(config);

        mockMvc.perform(get("/products/1/configuration"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product updatedProduct = Product.builder().id(1L).productName("Updated Phone").build();
        Mockito.when(productService.updateProduct(eq(1L), any(Product.class)))
                .thenReturn(updatedProduct);

        mockMvc.perform(patch("/products/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Updated Phone"));
    }
}