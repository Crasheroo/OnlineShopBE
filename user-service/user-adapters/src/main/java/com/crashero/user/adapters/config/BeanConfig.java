package com.crashero.user.adapters.config;

import com.crashero.user.core.UserService;
import com.crashero.user.core.port.out.CartPort;
import com.crashero.user.core.port.out.OrderPort;
import com.crashero.user.core.port.out.ProductPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public UserService userService(ProductPort productPort, CartPort cartPort, OrderPort orderPort) {
        return new UserService(productPort, cartPort, orderPort);
    }
}
