package com.snackbar.basket.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemEntity {
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private Integer cookingTime;
}
