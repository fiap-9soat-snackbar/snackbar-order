package com.snackbar.order.infrastructure.controllers;

import java.math.BigDecimal;

public record OrderItemResponse(
        int quantity,
        BigDecimal price
) {
}
