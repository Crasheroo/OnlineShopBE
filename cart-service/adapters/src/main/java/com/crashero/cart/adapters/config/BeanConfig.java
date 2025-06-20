package com.crashero.cart.adapters.config;

import com.crashero.core.service.CartPort;
import com.crashero.core.service.CartService;
import com.crashero.core.service.impl.CartServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CartServiceImpl cartService(CartPort cartPort) {
        return new CartServiceImpl(cartPort);
    }
}
