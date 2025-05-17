package com.crashero.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<SelectedConfiguration> selectedConfigurations;
    private Double additionalPrice;

    public Double getTotalPrice() {
        double base = (price != null ? price : 0.0);
        double extra = (additionalPrice != null ? additionalPrice : 0.0);
        return (base + extra) * (quantity != null ? quantity : 1);
    }
}