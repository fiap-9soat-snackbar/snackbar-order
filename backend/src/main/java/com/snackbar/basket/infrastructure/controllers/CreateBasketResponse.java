package com.snackbar.basket.infrastructure.controllers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record CreateBasketResponse(
        String id,
        String cpf,
        List<ItemResponse> items,
        BigDecimal totalPrice
) {}
