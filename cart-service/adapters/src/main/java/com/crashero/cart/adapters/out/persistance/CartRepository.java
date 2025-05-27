package com.crashero.cart.adapters.out.persistance;

import com.crashero.core.service.CartPort;
import com.crashero.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CartRepository implements CartPort {
    private final SpringDataCartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public Cart save(Cart cart) {
        CartEntity cartEntity = cartMapper.toEntity(cart);

        if (cartEntity.getItems() != null) {
            cartEntity.getItems().forEach(item -> item.setCart(cartEntity));
        }

        CartEntity saved = cartRepository.save(cartEntity);
        return cartMapper.toDomain(saved);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id)
                .map(cartMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }

    public List<Cart> findAll() {
        return cartRepository.findAll()
                .stream()
                .map(cartMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .map(cartMapper::toDomain);
    }

//    private CartEntity toEntity(Cart cart) {
//        if (cart == null) {
//            return null;
//        }
//
//        CartEntity entity = new CartEntity();
//        entity.setId(cart.getId());
//        entity.setUserId(cart.getUserId());
//
//        if (cart.getItems() != null) {
//            List<CartItemEntity> itemEntities = new ArrayList<>();
//            for (CartItem item : cart.getItems()) {
//                CartItemEntity itemEntity = toEntity(item);
//                itemEntities.add(itemEntity);
//            }
//            entity.setItems(itemEntities);
//        } else {
//            entity.setItems(null);
//        }
//
//        return entity;
//    }

//    private CartItemEntity toEntity(CartItem item) {
//        if (item == null) {
//            return null;
//        }
//
//        CartItemEntity entity = new CartItemEntity();
//        entity.setId(item.getId());
//        entity.setProductId(item.getProductId());
//        entity.setProductName(item.getProductName());
//        entity.setQuantity(item.getQuantity());
//        entity.setPrice(item.getPrice());
//        entity.setAdditionalPrice(item.getAdditionalPrice());
//
//        if (item.getSelectedConfigurations() != null) {
//            List<SelectedConfigurationEmbeddable> selectedConfigs = item.getSelectedConfigurations().stream()
//                    .map(cfg -> SelectedConfigurationEmbeddable.builder()
//                            .id(cfg.getId())
//                            .configurationName(cfg.getConfigurationName())
//                            .configurationDescription(cfg.getConfigurationDescription())
//                            .build())
//                    .toList();
//            entity.setSelectedConfigurations(selectedConfigs);
//        } else {
//            entity.setSelectedConfigurations(null);
//        }
//
//        return entity;
//    }
//
//    private Cart toDomain(CartEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//
//        Cart cart = new Cart();
//        cart.setId(entity.getId());
//        cart.setUserId(entity.getUserId());
//
//        if (entity.getItems() != null) {
//            List<CartItem> items = new ArrayList<>();
//            for (CartItemEntity itemEntity : entity.getItems()) {
//                CartItem item = toDomain(itemEntity);
//                items.add(item);
//            }
//            cart.setItems(items);
//        } else {
//            cart.setItems(null);
//        }
//
//        return cart;
//    }
//
//    private CartItem toDomain(CartItemEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//
//        CartItem item = new CartItem();
//        item.setId(entity.getId());
//        item.setProductId(entity.getProductId());
//        item.setProductName(entity.getProductName());
//        item.setQuantity(entity.getQuantity());
//        item.setPrice(entity.getPrice());
//        item.setAdditionalPrice(entity.getAdditionalPrice());
//
//        if (entity.getSelectedConfigurations() != null) {
//            List<SelectedConfiguration> selectedConfigs = entity.getSelectedConfigurations().stream()
//                    .map(cfgEmb -> SelectedConfiguration.builder()
//                            .id(cfgEmb.getId())
//                            .configurationName(cfgEmb.getConfigurationName())
//                            .configurationDescription(cfgEmb.getConfigurationDescription())
//                            .build())
//                    .toList();
//            item.setSelectedConfigurations(selectedConfigs);
//        } else {
//            item.setSelectedConfigurations(null);
//        }
//
//        return item;
//    }
}
