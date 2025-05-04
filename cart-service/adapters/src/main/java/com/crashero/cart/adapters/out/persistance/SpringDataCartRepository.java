package com.crashero.cart.adapters.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataCartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserId(Long userId);
}
