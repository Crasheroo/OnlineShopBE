package com.crashero.order.adapters.out.persistance;

import com.crashero.model.Order;
import com.crashero.model.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderItemEntity toEntity(OrderItem orderItem);

    OrderItem toDomain(OrderItemEntity orderItemEntity);

    List<OrderItemEntity> toItemEntityList(List<OrderItem> items);

    List<OrderItem> toItemDomainList(List<OrderItemEntity> items);

    OrderEntity toEntity(Order order);

    Order toDomain(OrderEntity orderEntity);
}
