package com.crashero.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductConfiguration {
    private Long id;
    private String configurationName;
    private String configurationDescription;
    private Double additionalPrice;
}
