package com.crashero.product.adapters.out.persistance;

import com.crashero.model.Product;
import com.crashero.model.ProductConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "configuration", source = "configuration")
    ProductEntity toEntity(Product product);

    @Mapping(target = "configuration", source = "configuration")
    Product toDomain(ProductEntity entity);

    ProductConfiguration toDomain(ProductConfigurationEntity entity);

    ProductConfigurationEntity toEntity(ProductConfiguration dto);

    List<ProductConfiguration> toDomain(List<ProductConfigurationEntity> productConfigurationEntities);

    List<ProductConfigurationEntity> toEntity(List<ProductConfiguration> productConfigurations);
}