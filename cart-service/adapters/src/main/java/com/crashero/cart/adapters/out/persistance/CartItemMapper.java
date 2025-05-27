package com.crashero.cart.adapters.out.persistance;

import com.crashero.model.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemEntity toEntity(CartItem cartItem);

    CartItem toDomain(CartItemEntity cartItemEntity);

    List<CartItemEntity> toEntityList(List<CartItem> items);

    List<CartItem> toDomainList(List<CartItemEntity> entities);
}
