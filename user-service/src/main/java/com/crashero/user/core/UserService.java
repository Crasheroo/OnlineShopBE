package com.crashero.user.core;

import com.crashero.model.*;
import com.crashero.model.exception.CartException;
import com.crashero.user.adapters.out.CartClient;
import com.crashero.user.adapters.out.OrderClient;
import com.crashero.user.adapters.out.ProductClient;
import com.crashero.user.model.event.CartEvent;
import com.crashero.user.model.event.CheckoutEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final ProductClient productClient;
    private final CartClient cartClient;
    private final OrderClient orderClient;
    private final KafkaSender kafkaSender;

    public UserService(ProductClient productClient, CartClient cartClient, OrderClient orderClient, KafkaSender kafkaSender) {
        this.productClient = productClient;
        this.cartClient = cartClient;
        this.orderClient = orderClient;
        this.kafkaSender = kafkaSender;
    }

    public PageableContentDTO<Product> browseProducts(Pageable pageable) {
        return productClient.getProducts(pageable);
    }

    public void addToCart(AddProductToCartCommand command) {
        Product product = productClient.getProductById(command.getProductId());
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

        cartClient.addProductToCart(cartCommand);
        kafkaSender.sendCartEvent(CartEvent.builder()
                .userId(command.getUserId())
                .productId(command.getProductId())
                .quantity(command.getQuantity())
                .productName(product.getProductName())
                .price(product.getPrice())
                .additionalPrice(additionalPrice)
                .build());
    }


    public Order checkout(Long cartId, Long userId) {
        Cart cart = cartClient.getCart(userId);
        validateCart(userId, cart);

        List<OrderItem> orderItems = cart.getItems().stream()
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

        Order order = orderClient.createOrder(userId, orderItems);

        cartClient.deleteCart(cartId);
        kafkaSender.sendCheckoutEvent(CheckoutEvent.builder()
                .userId(userId)
                .cartId(cartId)
                .items(orderItems.stream().map(item -> CheckoutEvent.CheckoutItem.builder()
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .totalPrice(item.getPrice())
                        .build()).toList())
                .build());
        return order;
    }

    public List<Order> getOrderHistory(Long userId) {
        return orderClient.getOrdersByUserId(userId);
    }

    public Cart getCartByUserId(Long userId) {
        return cartClient.getCart(userId);
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
}