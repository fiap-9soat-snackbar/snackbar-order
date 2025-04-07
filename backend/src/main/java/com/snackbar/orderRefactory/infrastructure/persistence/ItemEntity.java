package com.snackbar.orderRefactory.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ItemEntity {
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private Integer cookingTime;
    private String customization;
}
