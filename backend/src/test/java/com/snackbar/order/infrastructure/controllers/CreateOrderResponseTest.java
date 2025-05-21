package com.snackbar.order.infrastructure.controllers;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateOrderResponseTest {

    @Test
    public void testCreateOrderResponse_AllFields() {
        String orderId = "order123";
        String customerId = "cust001";
        Instant orderDate = Instant.now();
        List<OrderItemResponse> items = List.of(
                new OrderItemResponse(2, BigDecimal.valueOf(9.99)),
                new OrderItemResponse(1, BigDecimal.valueOf(19.99))
        );
        BigDecimal totalPrice = BigDecimal.valueOf(39.97);

        CreateOrderResponse response = new CreateOrderResponse(
                orderId,
                customerId,
                orderDate,
                items,
                totalPrice
        );

        assertEquals(orderId, response.orderId());
        assertEquals(customerId, response.customerId());
        assertEquals(orderDate, response.orderDate());
        assertEquals(items, response.items());
        assertEquals(totalPrice, response.totalPrice());
    }

    @Test
    public void testCreateOrderResponse_EmptyItems() {
        CreateOrderResponse response = new CreateOrderResponse(
                "order456",
                "cust002",
                Instant.EPOCH,
                List.of(),
                BigDecimal.ZERO
        );

        assertNotNull(response);
        assertTrue(response.items().isEmpty());
        assertEquals(BigDecimal.ZERO, response.totalPrice());
    }
}
