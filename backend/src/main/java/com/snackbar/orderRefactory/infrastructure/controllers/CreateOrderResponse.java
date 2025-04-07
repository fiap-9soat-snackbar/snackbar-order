package com.snackbar.orderRefactory.infrastructure.controllers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record CreateOrderResponse(
        String orderId,
        String customerId,
        Instant orderDate,
        List<OrderItemResponse> items,
        BigDecimal totalPrice
) {
}
