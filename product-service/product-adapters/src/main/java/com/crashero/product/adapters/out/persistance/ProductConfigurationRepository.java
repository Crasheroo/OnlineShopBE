package com.crashero.product.adapters.out.persistance;

import com.crashero.core.port.ProductConfigurationPort;
import com.crashero.model.ProductConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ProductConfigurationRepository implements ProductConfigurationPort {

    private final SpringDataProductConfigurationRepository jpaRepository;

    @Override
    public ProductConfiguration save(ProductConfiguration productConfiguration) {
        ProductConfigurationEntity entity = toEntity(productConfiguration);
        ProductConfigurationEntity saved = jpaRepository.save(entity);
        return toDto(saved);
    }

    @Override
    public Optional<ProductConfiguration> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<ProductConfiguration> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ProductConfigurationEntity toEntity(ProductConfiguration config) {
        if (config == null) return null;

        ProductConfigurationEntity entity = new ProductConfigurationEntity();
        entity.setId(config.getId());
        entity.setConfigurationName(config.getConfigurationName());
        entity.setConfigurationDescription(config.getConfigurationDescription());
        entity.setAdditionalPrice(config.getAdditionalPrice());
        return entity;
    }

    private ProductConfiguration toDto(ProductConfigurationEntity entity) {
        if (entity == null) return null;

        return ProductConfiguration.builder()
                .id(entity.getId())
                .configurationName(entity.getConfigurationName())
                .configurationDescription(entity.getConfigurationDescription())
                .additionalPrice(entity.getAdditionalPrice())
                .build();
    }
}
