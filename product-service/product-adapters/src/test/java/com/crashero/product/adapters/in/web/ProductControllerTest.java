package com.crashero.product.adapters.in.web;

import com.crashero.core.service.ProductService;
import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import com.crashero.model.ProductType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @MockitoBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnProductById() throws Exception {
        Product product = Product.builder()
                .id(1L)
                .productName("Laptop")
                .price(1200.0)
                .type(ProductType.ELECTRONICS)
                .build();

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1200.0));
    }

    @Test
    void shouldReturnAllProducts() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Product product1 = Product.builder().id(1L).productName("Phone").price(800.0).type(ProductType.ELECTRONICS).build();
        Product product2 = Product.builder().id(2L).productName("Tablet").price(500.0).type(ProductType.ELECTRONICS).build();
        List<Product> products = List.of(product1, product2);

        Page<Product> page = new PageImpl<>(List.of(product1, product2), pageable, 2);
        PageableContentDTO<Product> response = PageableContentDTO.from(page, products);

        when(productService.getAllProducts(pageable)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        Product input = Product.builder()
                .productName("TV")
                .price(1500.0)
                .type(ProductType.ELECTRONICS)
                .build();

        Product created = Product.builder()
                .id(3L)
                .productName("TV")
                .price(1500.0)
                .type(ProductType.ELECTRONICS)
                .build();

        when(productService.createProduct(input)).thenReturn(created);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.productName").value("TV"));
    }
}
