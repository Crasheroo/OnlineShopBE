package com.crashero.product.adapters.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductConfigurationRepository extends JpaRepository<ProductConfigurationEntity, Long> {
}
