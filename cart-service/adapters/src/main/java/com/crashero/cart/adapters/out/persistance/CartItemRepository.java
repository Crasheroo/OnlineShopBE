package com.crashero.cart.adapters.out.persistance;

import com.crashero.core.service.CartItemPort;
import com.crashero.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CartItemRepository implements CartItemPort {
    private final SpringDataCartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItem save(CartItem cartItem) {
        CartItemEntity cartItemEntity = cartItemMapper.toEntity(cartItem);
        CartItemEntity saved = cartItemRepository.save(cartItemEntity);
        return cartItemMapper.toDomain(saved);
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id)
                .map(cartItemMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }

//    private CartItemEntity toEntity(CartItem cartItem) {
//        if (cartItem == null) {
//            return null;
//        }
//
//        CartItemEntity entity = new CartItemEntity();
//        entity.setId(cartItem.getId());
//        entity.setProductId(cartItem.getProductId());
//        entity.setProductName(cartItem.getProductName());
//        entity.setQuantity(cartItem.getQuantity());
//        entity.setPrice(cartItem.getPrice());
//        return entity;
//    }
//
//    private CartItem toDomain(CartItemEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//
//        CartItem cartItem = new CartItem();
//        cartItem.setId(entity.getId());
//        cartItem.setProductId(entity.getProductId());
//        cartItem.setPrice(entity.getPrice());
//        cartItem.setProductName(entity.getProductName());
//        cartItem.setQuantity(entity.getQuantity());
//        return cartItem;
//    }
}
