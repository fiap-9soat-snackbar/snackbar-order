package com.snackbar.order.infrastructure.gateways;

import com.snackbar.order.domain.entity.Order;
import com.snackbar.order.domain.entity.OrderItem;
import com.snackbar.order.domain.valueobject.StatusOrder;
import com.snackbar.order.infrastructure.persistence.ItemEntity;
import com.snackbar.order.infrastructure.persistence.OrderEntity;
import com.snackbar.order.infrastructure.persistence.StatusOrderEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderEntityMapperTest {

    private final OrderEntityMapper mapper = new OrderEntityMapper();

    @Test
    void testToEntityAndToDomain() {
        OrderItem item = new OrderItem("Burger", 2, new BigDecimal("15.00"), 10, "No onions");
        Order order = new Order("1", "000001", Instant.now(), "12345678900", "João",
                List.of(item), StatusOrder.NOVO, "CARD", new BigDecimal("30.00"), 5);

        OrderEntity entity = mapper.toEntity(order);

        assertEquals(order.getId(), entity.getId());
        assertEquals(order.getOrderNumber(), entity.getOrderNumber());
        assertEquals(order.getCpf(), entity.getCpf());
        assertEquals(order.getName(), entity.getName());
        assertEquals(order.getPaymentMethod(), entity.getPaymentMethod());
        assertEquals(order.getTotalPrice(), entity.getTotalPrice());
        assertEquals(order.getRemainingTime(), entity.getRemainingTime());
        assertEquals(StatusOrderEntity.NOVO, entity.getStatusOrder());

        assertEquals(1, entity.getItems().size());
        ItemEntity itemEntity = entity.getItems().get(0);
        assertEquals("Burger", itemEntity.getName());
        assertEquals(2, itemEntity.getQuantity());
        assertEquals(new BigDecimal("15.00"), itemEntity.getPrice());
        assertEquals(10, itemEntity.getCookingTime());
        assertEquals("No onions", itemEntity.getCustomization());

        Order convertedOrder = mapper.toDomainObj(entity);

        assertEquals(order.getId(), convertedOrder.getId());
        assertEquals(order.getOrderNumber(), convertedOrder.getOrderNumber());
        assertEquals(order.getCpf(), convertedOrder.getCpf());
        assertEquals(order.getName(), convertedOrder.getName());
        assertEquals(order.getPaymentMethod(), convertedOrder.getPaymentMethod());
        assertEquals(order.getTotalPrice(), convertedOrder.getTotalPrice());
        assertEquals(order.getRemainingTime(), convertedOrder.getRemainingTime());
        assertEquals(StatusOrder.NOVO, convertedOrder.getStatusOrder());

        assertEquals(1, convertedOrder.getItems().size());
        OrderItem convertedItem = convertedOrder.getItems().get(0);
        assertEquals("Burger", convertedItem.getName());
        assertEquals(2, convertedItem.getQuantity());
        assertEquals(new BigDecimal("15.00"), convertedItem.getPrice());
        assertEquals(10, convertedItem.getCookingTime());
        assertEquals("No onions", convertedItem.getCustomization());
    }
}
