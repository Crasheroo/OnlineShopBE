package com.crashero.cart.adapters.in.web;

import com.crashero.core.service.CartService;
import com.crashero.model.AddProductToCart;
import com.crashero.model.Cart;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetCartById() throws Exception {
        Cart mockCart = new Cart();
        mockCart.setId(1L);
        Mockito.when(cartService.getCart(1L)).thenReturn(mockCart);

        mockMvc.perform(get("/carts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetCartByUserId() throws Exception {
        Cart mockCart = new Cart();
        mockCart.setUserId(10L);
        Mockito.when(cartService.getCartByUserId(10L)).thenReturn(mockCart);

        mockMvc.perform(get("/carts/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(10L));
    }

    @Test
    void testDeleteCart() throws Exception {
        doNothing().when(cartService).deleteCart(1L);

        mockMvc.perform(delete("/carts/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddProductToCart() throws Exception {
        AddProductToCart command = new AddProductToCart();
        command.setUserId(1L);
        command.setProductId(100L);
        command.setQuantity(2);

        doNothing().when(cartService).addProductToCart(any(AddProductToCart.class));

        mockMvc.perform(post("/carts/add-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllCarts() throws Exception {
        mockMvc.perform(get("/carts"))
                .andExpect(status().isOk());
    }
}