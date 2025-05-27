package com.crashero.order.adapters.out.persistance;

import com.crashero.core.service.OrderPort;
import com.crashero.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OrderRepository implements OrderPort {
    private final SpringDataOrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderMapper.toEntity(order);
        OrderEntity saved = orderRepository.save(orderEntity);
        return orderMapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDomain);
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderMapper::toDomain)
                .toList();
    }

//    private OrderEntity toEntity(Order order) {
//        if (order == null) {
//            return null;
//        }
//
//        OrderEntity entity = new OrderEntity();
//        entity.setId(order.getId());
//        entity.setUserId(order.getUserId());
//        entity.setTotalAmount(order.getTotalAmount());
//
//        if (order.getItems() != null) {
//            List<OrderItemEntity> items = order.getItems().stream()
//                    .map(this::toEntity)
//                    .toList();
//            entity.setItems(items);
//        } else {
//            entity.setItems(null);
//        }
//
//        return entity;
//    }
//
//    private OrderItemEntity toEntity(OrderItem item) {
//        if (item == null) {
//            return null;
//        }
//
//        OrderItemEntity entity = new OrderItemEntity();
//        entity.setId(item.getId());
//        entity.setProductName(item.getProductName());
//        entity.setQuantity(item.getQuantity());
//        entity.setPrice(item.getPrice());
//        return entity;
//    }
//
//    private Order toDomain(OrderEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//
//        return Order.builder()
//                .id(entity.getId())
//                .userId(entity.getUserId())
//                .totalAmount(entity.getTotalAmount())
//                .items(entity.getItems() != null
//                        ? entity.getItems().stream()
//                        .map(this::toDomain)
//                        .toList()
//                        : null)
//                .build();
//    }
//
//    private OrderItem toDomain(OrderItemEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//
//        return OrderItem.builder()
//                .id(entity.getId())
//                .productName(entity.getProductName())
//                .quantity(entity.getQuantity())
//                .price(entity.getPrice())
//                .build();
//    }
}
