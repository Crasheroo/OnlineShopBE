package com.crashero.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductToCartCommand {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private ProductConfigurationSelection configuration;
}
