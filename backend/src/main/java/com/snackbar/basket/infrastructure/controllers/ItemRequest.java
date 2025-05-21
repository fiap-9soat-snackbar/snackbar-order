package com.snackbar.basket.infrastructure.controllers;

import java.math.BigDecimal;

public record ItemRequest(
        String id,
        String name,
        Integer quantity,
        BigDecimal price,
        Integer cookingTime,
        String customization
) {}
