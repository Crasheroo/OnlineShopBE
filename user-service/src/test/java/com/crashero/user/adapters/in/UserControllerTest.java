package com.crashero.user.adapters.in;

import com.crashero.model.*;
import com.crashero.user.core.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

//    @Test
//    void shouldReturnAllProducts() throws Exception {
//        Product product = Product.builder().id(1L).productName("Phone").price(800.0).type(ProductType.ELECTRONICS).build();
//        when(userService.browseProducts()).thenReturn(List.of(product));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/ui/products"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].productName").value("Phone"));
//    }

//    @Test
//    void shouldAddProductToCart() throws Exception {
//        AddProductToCartCommand command = new AddProductToCartCommand(1L, 1L, 2);
//        doNothing().when(userService).addToCart(command);
//
//        mockMvc.perform(post("/ui/carts/add-product")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(command)))
//                .andExpect(status().isOk());
//    }

    @Test
    void shouldCheckoutCart() throws Exception {
        Order order = new Order();
        order.setId(10L);
        when(userService.checkout(1L, 2L)).thenReturn(order);

        mockMvc.perform(post("/ui/checkout/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void shouldReturnUserOrders() throws Exception {
        Order order = new Order();
        order.setId(11L);
        when(userService.getOrderHistory(1L)).thenReturn(List.of(order));

        mockMvc.perform(MockMvcRequestBuilders.get("/ui/orders/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(11));
    }

    @Test
    void shouldReturnCartById() throws Exception {
        Cart cart = new Cart();
        cart.setId(5L);
        when(userService.getCartById(5L)).thenReturn(cart);

        mockMvc.perform(MockMvcRequestBuilders.get("/ui/carts/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

//    @Test
//    void shouldReturnProductConfiguration() throws Exception {
//        Product product = Product.builder().id(1L).productName("Phone").price(800.0).type(ProductType.SMARTPHONE).build();
//        List<Integer> batteryCapacities = List.of(2000, 3000, 4000);
//
//        SmartphoneConfiguration config = SmartphoneConfiguration.builder().batteryCapacities(batteryCapacities).build();
//        when(userService.getProductById(1L)).thenReturn(product);
//        when(userService.getProductConfiguration(1L)).thenReturn(config);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/ui/products/1/configuration"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.batteryCapacities[0]").value(2000))
//                .andExpect(jsonPath("$.batteryCapacities[1]").value(3000))
//                .andExpect(jsonPath("$.batteryCapacities[2]").value(4000));
//    }

//    @Test
//    void shouldReturnProductById() throws Exception {
//        Product product = Product.builder().id(2L).productName("TV").build();
//        when(userService.getProductById(2L)).thenReturn(product);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/ui/products/2"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.productName").value("TV"));
//    }

    @Test
    void shouldReturnInvoices() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setId(9L);
        when(userService.getInvoicesByUserId(4L)).thenReturn(List.of(invoice));

        mockMvc.perform(MockMvcRequestBuilders.get("/ui/orders/invoice/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(9));
    }
}
