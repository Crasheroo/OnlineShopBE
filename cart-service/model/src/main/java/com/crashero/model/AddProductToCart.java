package com.crashero.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductToCart {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private String productName;
    private Double price;
    private List<SelectedConfiguration> selectedConfigurations;
    private Double additionalPrice;
}
