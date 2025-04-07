package com.snackbar.orderRefactory.infrastructure.gateways;

import com.snackbar.orderRefactory.domain.entity.Order;
import com.snackbar.orderRefactory.domain.entity.OrderItem;
import com.snackbar.orderRefactory.domain.valueobject.StatusOrder;
import com.snackbar.orderRefactory.infrastructure.persistence.OrderEntity;
import com.snackbar.orderRefactory.infrastructure.persistence.ItemEntity;
import com.snackbar.orderRefactory.infrastructure.persistence.StatusOrderEntity;

import java.util.List;
import java.util.stream.Collectors;

public class OrderEntityMapper {

    public OrderEntity toEntity(Order orderDomainObj) {
        List<ItemEntity> itemEntities = orderDomainObj.getItems().stream()
                .map(this::toItemEntity)
                .collect(Collectors.toList());

        StatusOrderEntity statusOrder = toPersistenceStatusOrder(orderDomainObj.getStatusOrder());

        return new OrderEntity(
                orderDomainObj.getId(),
                orderDomainObj.getOrderNumber(),
                orderDomainObj.getOrderDateTime(),
                orderDomainObj.getCpf(),
                orderDomainObj.getName(),
                itemEntities,
                statusOrder,
                orderDomainObj.getPaymentMethod(),
                orderDomainObj.getTotalPrice(),
                orderDomainObj.getRemainingTime()
        );
    }

    public Order toDomainObj(OrderEntity orderEntity) {
        List<OrderItem> items = orderEntity.getItems().stream()
                .map(this::toDomainItem)
                .collect(Collectors.toList());

        StatusOrder statusOrder = toDomainStatusOrder(orderEntity.getStatusOrder());

        return new Order(
                orderEntity.getId(),
                orderEntity.getOrderNumber(),
                orderEntity.getOrderDateTime(),
                orderEntity.getCpf(),
                orderEntity.getName(),
                items,
                statusOrder,
                orderEntity.getPaymentMethod(),
                orderEntity.getTotalPrice(),
                orderEntity.getRemainingTime()
        );
    }

    private ItemEntity toItemEntity(OrderItem item) {
        return new ItemEntity(
                item.getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getCookingTime(),
                item.getCustomization()
        );
    }

    private OrderItem toDomainItem(ItemEntity itemEntity) {
        return new OrderItem(
                itemEntity.getName(),
                itemEntity.getQuantity(),
                itemEntity.getPrice(),
                itemEntity.getCookingTime(),
                itemEntity.getCustomization()
        );
    }

    private StatusOrderEntity toPersistenceStatusOrder(StatusOrder statusOrder) {
        return StatusOrderEntity.valueOf(statusOrder.name());
    }

    private StatusOrder toDomainStatusOrder(StatusOrderEntity statusOrder) {
        return StatusOrder.valueOf(statusOrder.name());
    }
}
