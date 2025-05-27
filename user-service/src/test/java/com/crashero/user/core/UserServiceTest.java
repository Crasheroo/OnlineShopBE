package com.crashero.user.core;

import com.crashero.model.*;
import com.crashero.model.exception.CartException;
import com.crashero.user.adapters.out.CartClient;
import com.crashero.user.adapters.out.OrderClient;
import com.crashero.user.adapters.out.ProductClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private ProductClient productClient;
    private CartClient cartClient;
    private UserService userService;
    private OrderClient orderClient;
    private KafkaSender kafkaSender;

    @BeforeEach
    public void setUp() {
        productClient = mock(ProductClient.class);
        cartClient = mock(CartClient.class);
        orderClient = mock(OrderClient.class);
        userService = new UserService(productClient, cartClient, orderClient, kafkaSender);
    }

//    @Test
//    void shouldBrowseProducts() {
//        List<Product> products = List.of(new Product(1L, "Product A", 100.0, ProductType.ELECTRONICS));
//        when(productClient.getProducts()).thenReturn(products);
//
//        List<Product> result = userService.browseProducts();
//
//        assertThat(result).isEqualTo(products);
//    }

//    @Test
//    void shouldAddProductToCart() {
//        AddProductToCartCommand command = new AddProductToCartCommand(1L, 2L, 3);
//        userService.addToCart(command);
//
//        verify(cartClient).addProductToCart(command);
//    }

    @Test
    void shouldThrowWhenCartUserMismatch() {
        Cart cart = new Cart(1L, 2L, List.of());
        when(cartClient.getCart(1L)).thenReturn(cart);

        assertThatThrownBy(() -> userService.checkout(1L, 3L))
                .isInstanceOf(CartException.class)
                .hasMessageContaining("Cart does not belong to user!");
    }

//    @Test
//    void shouldCheckoutOrderAndDeleteCart() {
//        CartItem item = new CartItem(1L, 1L,"Phone", 1);
//        Cart cart = new Cart(1L, 2L, List.of(item));
//        Product product = new Product(1L, "Phone", 500.0, ProductType.SMARTPHONE);
//        Order order = new Order();
//
//        when(cartClient.getCart(1L)).thenReturn(cart);
//        when(productClient.getProducts()).thenReturn(List.of(product));
//        when(orderClient.createOrder(eq(2L), any())).thenReturn(order);
//
//        Order result = userService.checkout(1L, 2L);
//
//        assertThat(result).isEqualTo(order);
//        verify(cartClient).deleteCart(1L);
//    }

    @Test
    void shouldGetOrderHistory() {
        List<Order> orders = List.of(new Order());
        when(orderClient.getOrdersByUserId(1L)).thenReturn(orders);

        List<Order> result = userService.getOrderHistory(1L);

        assertThat(result).isEqualTo(orders);
    }

    @Test
    void shouldGetCartById() {
        Cart cart = new Cart();
        when(cartClient.getCart(1L)).thenReturn(cart);

        assertThat(userService.getCartByUserId(1L)).isEqualTo(cart);
    }

    @Test
    void shouldReturnProductConfiguration() {
        Object config = new Object();
        when(productClient.getProductConfiguration(1L)).thenReturn(config);

        assertThat(userService.getProductConfiguration(1L)).isEqualTo(config);
    }

    @Test
    void shouldReturnProductById() {
        Product product = new Product();
        when(productClient.getProductById(1L)).thenReturn(product);

        assertThat(userService.getProductById(1L)).isEqualTo(product);
    }

    @Test
    void shouldReturnInvoicesByUserId() {
        List<Invoice> invoices = List.of(new Invoice());
        when(orderClient.getInvoicesByUserId(1L)).thenReturn(invoices);

        assertThat(userService.getInvoicesByUserId(1L)).isEqualTo(invoices);
    }
}
