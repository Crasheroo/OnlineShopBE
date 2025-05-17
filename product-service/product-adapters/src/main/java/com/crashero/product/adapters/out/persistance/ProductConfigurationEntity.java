package com.crashero.product.adapters.out.persistance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_configurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String configurationName;
    private String configurationDescription;
    private Double additionalPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_Id")
    private ProductEntity product;
}
