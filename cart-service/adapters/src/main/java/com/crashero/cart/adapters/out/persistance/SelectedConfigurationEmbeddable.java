package com.crashero.cart.adapters.out.persistance;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectedConfigurationEmbeddable {
    private Long id;
    private String configurationName;
    private String configurationDescription;
}
