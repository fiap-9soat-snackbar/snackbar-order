package com.snackbar.order.infrastructure.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemRequest {
    private String id;
    private String name;
    private int quantity;
    private BigDecimal price;
    private Integer cookingTime;
    private String customization;
}
