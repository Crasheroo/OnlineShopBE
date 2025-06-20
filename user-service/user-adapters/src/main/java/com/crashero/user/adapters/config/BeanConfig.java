package com.crashero.user.adapters.config;

import com.crashero.user.core.port.out.CartPort;
import com.crashero.user.core.port.out.OrderPort;
import com.crashero.user.core.port.out.ProductPort;
import com.crashero.user.core.service.impl.AdminServiceImpl;
import com.crashero.user.core.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public UserServiceImpl userService(ProductPort productPort, CartPort cartPort, OrderPort orderPort) {
        return new UserServiceImpl(productPort, cartPort, orderPort);
    }

    @Bean
    public AdminServiceImpl adminService(ProductPort productPort, CartPort cartPort) {
        return new AdminServiceImpl(productPort, cartPort);
    }

}
