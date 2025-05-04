package com.crashero.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private ProductConfigurationSelection configuration;

    public Double getTotalPrice() {
        if (price == null || quantity == null) {
            return 0.0;
        }
        return price * quantity;
    }
}
