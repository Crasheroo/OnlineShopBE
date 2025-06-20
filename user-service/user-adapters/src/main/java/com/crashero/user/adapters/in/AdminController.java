package com.crashero.user.adapters.in;

import com.crashero.model.Cart;
import com.crashero.model.CreateProductCommand;
import com.crashero.model.Product;
import com.crashero.user.core.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@RolesAllowed("admin")
public class AdminController {
    private final AdminService adminService;
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    // Cart
    @Operation(summary = "Get all carts",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Carts found")
    @GetMapping("/carts")
    public List<Cart> getCarts() {
        log.info("Get all carts");
        return adminService.getCarts();
    }

    // Product
    @Operation(summary = "Create product",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Product created",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))})
    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductCommand command) {
        log.info("Create product");
        return adminService.createProduct(command);
    }

    @Operation(summary = "Update product by productId",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Product found and updated",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))})
    @PatchMapping("/products/{productId}/update")
    public Product updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product) {
        log.info("Update product");
        return adminService.updateProduct(productId, product);
    }

    @Operation(summary = "Delete product",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Product found and deleted")
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        log.info("Delete product");
        adminService.deleteProduct(id);
    }
}
