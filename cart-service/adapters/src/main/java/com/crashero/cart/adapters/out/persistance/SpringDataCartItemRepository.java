package com.crashero.cart.adapters.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCartItemRepository extends JpaRepository<CartItemEntity, Long> {
}
