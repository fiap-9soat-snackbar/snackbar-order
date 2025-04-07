package com.snackbar.orderRefactory.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItem {
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private Integer cookingTime;
    private String customization;

    public OrderItem(String name, Integer quantity, BigDecimal price, Integer cookingTime, String customization) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.cookingTime = cookingTime;
        this.customization = customization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getCustomization() {
        return customization;
    }

    public void setCustomization(String customization) {
        this.customization = customization;
    }
}
