package com.crashero.model.configuration;

import com.crashero.model.ProductType;
import lombok.Data;

@Data
public class CreateProductCommand {
    private String productName;
    private Double price;
    private ProductType type;
}
