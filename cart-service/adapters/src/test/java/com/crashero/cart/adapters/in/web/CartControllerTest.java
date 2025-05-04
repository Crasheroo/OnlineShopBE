package com.crashero.cart.adapters.in.web;//package com.crashero.cart.adapters.in.web;
//
//import com.crashero.core.service.CartService;
//import com.crashero.model.Cart;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.List;
//
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CartController.class)
//public class CartControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private CartService cartService;
//
//    @Test
//    void getCart_shouldReturnCart() throws Exception {
//        Cart cart = new Cart(1L, 1L, List.of());
//        when(cartService.getCart(1L)).thenReturn(cart);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/carts/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getCartByUserId_shouldReturnCart() throws Exception {
//        Cart cart = new Cart(1L, 1L, List.of());
//        when(cartService.getCartByUserId(1L)).thenReturn(cart);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/carts/user/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void deleteCart_shouldWork() throws Exception {
//        doNothing().when(cartService).deleteCart(1L);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void addProductToCart_shouldWork() throws Exception {
//        String json = """
//            {
//              "userId": 1,
//              "productId": 2,
//              "quantity": 3
//            }
//        """;
//
//        doNothing().when(cartService).addProductToCart(1L, 2L, 3);
//        mockMvc.perform(MockMvcRequestBuilders.post("/carts/add-product")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getAllCarts_shouldReturnCarts() throws Exception {
//        when(cartService.getAllCarts()).thenReturn(List.of(new Cart(1L, 1L, List.of())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/carts"))
//                .andExpect(status().isOk());
//    }
//}
