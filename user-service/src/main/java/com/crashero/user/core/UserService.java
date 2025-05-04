package com.crashero.user.core;

import com.crashero.common.exception.CartException;
import com.crashero.model.*;
import com.crashero.user.adapters.out.CartClient;
import com.crashero.user.adapters.out.OrderClient;
import com.crashero.user.adapters.out.ProductClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final ProductClient productClient;
    private final CartClient cartClient;
    private final OrderClient orderClient;

    public UserService(ProductClient productClient, CartClient cartClient, OrderClient orderClient) {
        this.productClient = productClient;
        this.cartClient = cartClient;
        this.orderClient = orderClient;
    }

    public PageableContentDTO<Product> browseProducts() {
        return productClient.getProducts();
    }

    public void addToCart(AddProductToCartCommand command) {
        Product product = productClient.getProductById(command.getProductId());

        validateConfigurationForProduct(product, command.getConfiguration());

        AddProductToCart cartCommand = AddProductToCart.builder()
                .userId(command.getUserId())
                .productId(command.getProductId())
                .quantity(command.getQuantity())
                .productName(product.getProductName())
                .price(product.getPrice())
                .configuration(command.getConfiguration())
                .build();

        cartClient.addProductToCart(cartCommand);
    }

    public Order checkout(Long cartId, Long userId) {
        Cart cart = cartClient.getCart(cartId);

        if (!cart.getUserId().equals(userId)) {
            throw new CartException("Cart does not belong to user!", HttpStatus.CONFLICT);
        }

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(item -> {
                    Product product = productClient.getProductById(item.getProductId());
                    double totalPrice = product.getPrice() * item.getQuantity();
                    return OrderItem.builder()
                            .productName(item.getProductName())
                            .quantity(item.getQuantity())
                            .price(totalPrice)
                            .build();
                })
                .toList();

        Order order = orderClient.createOrder(userId, orderItems);

        cartClient.deleteCart(cartId);

        return order;
    }

    public List<Order> getOrderHistory(Long userId) {
        return orderClient.getOrdersByUserId(userId);
    }

    public Cart getCartById(Long cartId) {
        return cartClient.getCart(cartId);
    }

    public Object getProductConfiguration(Long id) {
        return productClient.getProductConfiguration(id);
    }

    public Product getProductById(Long productId) {
        return productClient.getProductById(productId);
    }

    public List<Invoice> getInvoicesByUserId(Long userId) {
        return orderClient.getInvoicesByUserId(userId);
    }

    private void validateConfigurationForProduct(Product product, ProductConfigurationSelection config) {
        ProductType type = product.getType();

        boolean configMissing = config == null || config.getOptions() == null || config.getOptions().isEmpty();
        boolean configPresent = !configMissing;

        switch (type) {
            case SMARTPHONE, COMPUTER -> {
                if (configMissing) {
                    throw new IllegalArgumentException("Configuration is required for product type: " + type);
                }
            }
            case ELECTRONICS -> {
                if (configPresent) {
                    throw new IllegalArgumentException("Configuration is not allowed for product type: ELECTRONICS");
                }
            }
        }
    }

}
