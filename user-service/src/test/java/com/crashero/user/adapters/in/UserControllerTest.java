package com.crashero.user.adapters.in;

import com.crashero.model.*;
import com.crashero.user.core.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddToCart() throws Exception {
        AddProductToCartCommand command = new AddProductToCartCommand();
        command.setUserId(1L);
        command.setProductId(100L);
        command.setQuantity(2);

        Mockito.doNothing().when(userService).addToCart(any(AddProductToCartCommand.class));

        mockMvc.perform(post("/ui/carts/add-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk());
    }

    @Test
    void testCheckout() throws Exception {
        Order order = new Order();
        order.setId(123L);

        Mockito.when(userService.checkout(1L, 10L)).thenReturn(order);

        mockMvc.perform(post("/ui/checkout/1/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123L));
    }

    @Test
    void testGetOrderHistory() throws Exception {
        Order order = new Order();
        order.setUserId(10L);

        Mockito.when(userService.getOrderHistory(10L)).thenReturn(List.of(order));

        mockMvc.perform(get("/ui/orders/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(10L));
    }

    @Test
    void testGetCartById() throws Exception {
        Cart cart = new Cart();
        cart.setId(5L);

        Mockito.when(userService.getCartByUserId(5L)).thenReturn(cart);

        mockMvc.perform(get("/ui/carts/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L));
    }

    @Test
    void testGetProductConfiguration() throws Exception {
        var config = Map.of("option", "value");

        Mockito.when(userService.getProductConfiguration(100L)).thenReturn(config);

        mockMvc.perform(get("/ui/products/100/configuration")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.option").value("value"));
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = Product.builder().id(101L).productName("Headphones").build();

        Mockito.when(userService.getProductById(101L)).thenReturn(product);

        mockMvc.perform(get("/ui/products/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Headphones"));
    }

    @Test
    void testGetInvoicesByUserId() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setUserId(55L);

        Mockito.when(userService.getInvoicesByUserId(55L)).thenReturn(List.of(invoice));

        mockMvc.perform(get("/ui/orders/invoice/55"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(55L));
    }
}
