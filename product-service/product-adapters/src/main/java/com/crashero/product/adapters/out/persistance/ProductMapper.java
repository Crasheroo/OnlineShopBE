package com.crashero.product.adapters.out.persistance;

import com.crashero.model.Product;
import com.crashero.model.ProductConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(Product product);

    Product toDomain(ProductEntity entity);

    List<ProductEntity> toEntityList(List<Product> products);

    List<Product> toDomainList(List<ProductEntity> entities);

    ProductConfigurationEntity toEntity(ProductConfiguration configuration);
    ProductConfiguration toDomain(ProductConfigurationEntity entity);
    List<ProductConfigurationEntity> toEntity(List<ProductConfiguration> configurations);
    List<ProductConfiguration> toDomain(List<ProductConfigurationEntity> entities);

}