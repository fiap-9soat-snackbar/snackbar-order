package com.snackbar.basket.domain.entity;

import java.math.BigDecimal;

public record Item(
        String name,
        Integer quantity,
        BigDecimal price,
        Integer cookingTime
) {}
