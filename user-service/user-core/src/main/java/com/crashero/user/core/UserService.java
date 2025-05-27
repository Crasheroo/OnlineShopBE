package com.crashero.user.core;

import com.crashero.model.*;
import com.crashero.model.exception.CartException;
import com.crashero.user.core.port.out.CartPort;
import com.crashero.user.core.port.out.OrderPort;
import com.crashero.user.core.port.out.ProductPort;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final ProductPort productPort;
    private final CartPort cartPort;
    private final OrderPort orderPort;

    public UserService(ProductPort productPort, CartPort cartPort, OrderPort orderPort) {
        this.productPort = productPort;
        this.cartPort = cartPort;
        this.orderPort = orderPort;
    }

    public PageableContentDTO<Product> browseProducts(Pageable pageable) {
        return productPort.getProducts(pageable);
    }

    public void addToCart(AddProductToCartCommand command) {
        Product product = productPort.getProductById(command.getProductId());
        List<ProductConfiguration> selectedConfigs = getSelectedConfigurations(product, command);
        List<SelectedConfiguration> selectedConfigurations = mapToSelectedConfigurations(selectedConfigs);
        double additionalPrice = calculateAdditionalPrice(selectedConfigs);

        AddProductToCart cartCommand = AddProductToCart.builder()
                .userId(command.getUserId())
                .productId(command.getProductId())
                .quantity(command.getQuantity())
                .productName(product.getProductName())
                .price(product.getPrice())
                .selectedConfigurations(selectedConfigurations)
                .additionalPrice(additionalPrice)
                .build();

        cartPort.addProductToCart(cartCommand);
    }


    public Order checkout(Long cartId, Long userId) {
        Cart cart = cartPort.getCart(userId);
        validateCart(userId, cart);

        List<OrderItem> orderItems = getItems(cart);

        Order order = orderPort.createOrder(userId, orderItems);

        cartPort.deleteCart(cartId);
        return order;
    }

    public List<Order> getOrderHistory(Long userId) {
        return orderPort.getOrdersByUserId(userId);
    }

    public Cart getCartByUserId(Long userId) {
        return cartPort.getCart(userId);
    }

    public Object getProductConfiguration(Long id) {
        return productPort.getProductConfiguration(id);
    }

    public Product getProductById(Long productId) {
        return productPort.getProductById(productId);
    }

    public List<Invoice> getInvoicesByUserId(Long userId) {
        return orderPort.getInvoicesByUserId(userId);
    }

    private void validateCart(Long userId, Cart cart) {
        if (cart == null) {
            throw new CartException("Cart not found");
        }

        if (!cart.getUserId().equals(userId)) {
            throw new CartException("Cart does not belong to user!");
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new CartException("Cart is empty");
        }
    }

    private List<ProductConfiguration> getSelectedConfigurations(Product product, AddProductToCartCommand command) {
        List<Long> configIds = Optional.ofNullable(command.getConfigurationIds()).orElse(List.of());
        return product.getConfiguration().stream()
                .filter(cfg -> configIds.contains(cfg.getId()))
                .toList();
    }

    private List<SelectedConfiguration> mapToSelectedConfigurations(List<ProductConfiguration> configs) {
        return configs.stream()
                .map(cfg -> SelectedConfiguration.builder()
                        .id(cfg.getId())
                        .configurationName(cfg.getConfigurationName())
                        .configurationDescription(cfg.getConfigurationDescription())
                        .build())
                .toList();
    }

    private double calculateAdditionalPrice(List<ProductConfiguration> configs) {
        return configs.stream()
                .mapToDouble(ProductConfiguration::getAdditionalPrice)
                .sum();
    }

    private List<OrderItem> getItems(Cart cart) {
        return cart.getItems().stream()
                .map(item -> {
                    double basePrice = Optional.ofNullable(item.getPrice()).orElse(0.0);
                    double additional = Optional.ofNullable(item.getAdditionalPrice()).orElse(0.0);
                    int quantity = Optional.ofNullable(item.getQuantity()).orElse(1);

                    double totalPrice = (basePrice + additional) * quantity;

                    return OrderItem.builder()
                            .productName(item.getProductName())
                            .quantity(quantity)
                            .price(totalPrice)
                            .build();
                })
                .toList();
    }
}