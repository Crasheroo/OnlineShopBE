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
public class AddProductToCartCommand {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private List<Long> configurationIds;
}
