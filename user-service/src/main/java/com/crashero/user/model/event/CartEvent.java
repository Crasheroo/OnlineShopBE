package com.crashero.user.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartEvent {
    private Long userId;
    private Long productId;
    private int quantity;
    private String productName;
    private Double price;
    private Double additionalPrice;
}
