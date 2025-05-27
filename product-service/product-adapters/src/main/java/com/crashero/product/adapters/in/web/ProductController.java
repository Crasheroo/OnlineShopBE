package com.crashero.product.adapters.in.web;

import com.crashero.core.service.ProductService;
import com.crashero.model.CreateProductCommand;
import com.crashero.model.PageableContentDTO;
import com.crashero.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Operation(summary = "Create product")
    @ApiResponse(responseCode = "200", description = "Product created",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))})
    @PostMapping
    public Product createProduct(@RequestBody CreateProductCommand command) {
        Product product = Product.builder()
                .productName(command.getProductName())
                .price(command.getPrice())
                .type(command.getType())
                .configuration(command.getConfiguration())
                .build();
        log.info("Creating product: {}", command);
        return productService.createProduct(product);
    }

    @Operation(summary = "Get product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "500", description = "Product not found")
    })
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        log.info("Get product by id: {}", id);
        return productService.getProductById(id);
    }

    @Operation(summary = "Delete product by it's productId")
    @ApiResponse(responseCode = "200", description = "Product found")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        log.info("Delete product by id: {}", id);
        productService.deleteProduct(id);
    }

    @Operation(summary = "Get all products")
    @GetMapping
    public PageableContentDTO<Product> getAllProducts(@ParameterObject Pageable pageable) {
        log.info("Get all products");
        return productService.getAllProducts(pageable);
    }

    @Operation(summary = "Get product configuration by it's productId")
    @GetMapping("/{id}/configuration")
    public Object getProductConfiguration(@PathVariable("id") Long id) {
        log.info("Get product configuration by id: {}", id);
        return productService.getProductConfiguration(id);
    }

    @Operation(summary = "Update products data")
    @ApiResponse(responseCode = "200", description = "Product found and updated",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))})
    @PatchMapping("/{productId}/update")
    public Product updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product) {
        log.info("Update product by id: {}", productId);
        return productService.updateProduct(productId, product);
    }
}
