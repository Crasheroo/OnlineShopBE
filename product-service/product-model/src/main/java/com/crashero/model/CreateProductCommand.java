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
public class CreateProductCommand {
    private String productName;
    private Double price;
    private ProductType type;
    private List<ProductConfiguration> configuration;
}
