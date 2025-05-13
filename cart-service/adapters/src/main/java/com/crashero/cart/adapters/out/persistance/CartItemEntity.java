package com.crashero.cart.adapters.out.persistance;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;
    private String productName;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cart;
    private Double price;

    @ElementCollection
    @CollectionTable(name = "cart_item_configuration", joinColumns = @JoinColumn(name = "cart_item_id"))
    @MapKeyColumn(name = "configuration_key")
    @Column(name = "configuration_value")
    private Map<String, String> configuration;
}
