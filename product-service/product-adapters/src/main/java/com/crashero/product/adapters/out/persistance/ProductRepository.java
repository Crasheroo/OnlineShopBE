package com.crashero.product.adapters.out.persistance;

import com.crashero.core.service.ProductPort;
import com.crashero.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ProductRepository implements ProductPort {
    private final SpringDataProductRepository productRepository;

    @Override
    public Product save(Product product) {
        ProductEntity entity = toEntity(product);
        ProductEntity saved = productRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Page<ProductEntity> entities = productRepository.findAll(pageable);
        return entities.map(this::toDomain);
    }

    private ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }

        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setProductName(product.getProductName());
        entity.setPrice(product.getPrice());
        entity.setType(product.getType());
        return entity;
    }

    private Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return Product.builder()
                .id(entity.getId())
                .productName(entity.getProductName())
                .price(entity.getPrice())
                .type(entity.getType())
                .build();
    }
}
