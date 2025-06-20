package com.crashero.product.adapters.out.persistance;

import com.crashero.core.port.ProductPort;
import com.crashero.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ProductRepository implements ProductPort {
    private final SpringDataProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public Product save(Product product) {
        ProductEntity entity = productMapper.toEntity(product);

        if (entity.getConfiguration() != null) {
            entity.getConfiguration().forEach(config -> config.setProduct(entity));
        }

        ProductEntity saved = productRepository.save(entity);
        return productMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDomain);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDomain);
    }

//    private ProductEntity toEntity(Product product) {
//        if (product == null) {
//            return null;
//        }
//
//        List<ProductConfigurationEntity> configEntities = product.getConfiguration() == null
//                ? Collections.emptyList()
//                : product.getConfiguration().stream()
//                .map(this::toEntity)
//                .toList();
//
//        ProductEntity entity = new ProductEntity();
//        entity.setId(product.getId());
//        entity.setProductName(product.getProductName());
//        entity.setPrice(product.getPrice());
//        entity.setType(product.getType());
//        entity.setConfiguration(configEntities);
//
//        configEntities.forEach(config -> config.setProduct(entity));
//
//        return entity;
//    }
//
//    private Product toDomain(ProductEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//
//        List<ProductConfiguration> configurations = entity.getConfiguration() == null
//                ? Collections.emptyList()
//                : entity.getConfiguration().stream()
//                .map(this::toDomain)
//                .toList();
//
//        return Product.builder()
//                .id(entity.getId())
//                .productName(entity.getProductName())
//                .price(entity.getPrice())
//                .type(entity.getType())
//                .configuration(configurations)
//                .build();
//    }
//
//    private ProductConfigurationEntity toEntity(ProductConfiguration config) {
//        if (config == null) {
//            return null;
//        }
//
//        ProductConfigurationEntity entity = new ProductConfigurationEntity();
//        entity.setId(config.getId());
//        entity.setConfigurationName(config.getConfigurationName());
//        entity.setConfigurationDescription(config.getConfigurationDescription());
//        entity.setAdditionalPrice(config.getAdditionalPrice());
//        return entity;
//    }
//
//    private ProductConfiguration toDomain(ProductConfigurationEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//
//        return ProductConfiguration.builder()
//                .id(entity.getId())
//                .configurationName(entity.getConfigurationName())
//                .configurationDescription(entity.getConfigurationDescription())
//                .additionalPrice(entity.getAdditionalPrice())
//                .build();
//    }
}
