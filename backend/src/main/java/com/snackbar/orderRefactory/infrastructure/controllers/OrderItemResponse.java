package com.snackbar.orderRefactory.infrastructure.controllers;

import java.math.BigDecimal;

public record OrderItemResponse(
        int quantity,
        BigDecimal price
) {
}
