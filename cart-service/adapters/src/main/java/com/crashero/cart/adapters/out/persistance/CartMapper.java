package com.crashero.cart.adapters.out.persistance;

import com.crashero.model.Cart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {
    CartEntity toEntity(Cart cart);

    Cart toDomain(CartEntity cartEntity);

    List<Cart> toDomainList(List<CartEntity> cartEntities);
}
