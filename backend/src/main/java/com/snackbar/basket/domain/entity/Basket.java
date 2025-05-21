package com.snackbar.basket.domain.entity;

import java.math.BigDecimal;
import java.util.List;

public record Basket(
        String id,
        String cpf,
        List<Item> items,
        BigDecimal totalPrice
) {}

