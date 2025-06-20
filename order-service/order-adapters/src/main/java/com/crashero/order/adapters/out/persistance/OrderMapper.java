package com.crashero.order.adapters.out.persistance;

import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderEntity toEntity(Order order);

    Order toDomain(OrderEntity orderEntity);

    OrderItemEntity toEntity(OrderItem orderItem);

    OrderItem toDomain(OrderItemEntity orderItemEntity);
}
