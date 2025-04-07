package com.snackbar.orderRefactory.infrastructure.persistence;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "orders")
@AllArgsConstructor
public class OrderEntity {

    @Id

    private String id;
    private String orderNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant orderDateTime;
    private String cpf; // Mandatory field for creating an order
    private String name;
    private List<ItemEntity> items = new ArrayList<>();
    private StatusOrderEntity statusOrder;
    private String paymentMethod;
    private BigDecimal totalPrice;    
    private long remainingTime;
    

    // Getters e Setters

    public long getRemainingTime() {
        return remainingTime;
    }

    public void calculateRemainingTime() {
        Instant now = Instant.now();
        long elapsedTime = java.time.Duration.between(orderDateTime, now).toMinutes();
        this.remainingTime = Math.max(0, getWaitingTime() - elapsedTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Instant getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Instant orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> itemEntities) {
        this.items = itemEntities;
    }


    public int getWaitingTime() {
        return items.stream()
                .mapToInt(itemEntity -> itemEntity.getCookingTime() * itemEntity.getQuantity())
                .sum();
    }

    // Removed setWaitingTime as it's now calculated dynamically

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public StatusOrderEntity getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrderEntity statusOrder) {
        this.statusOrder = statusOrder;
    }

    public static String generateOrderNumber(String lastOrderNumber) {
        if (lastOrderNumber == null || lastOrderNumber.isEmpty()) {
            return "000001";
        }
        int nextNumber = Integer.parseInt(lastOrderNumber) + 1;
        return String.format("%06d", nextNumber);
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


}

// Item class has been moved to a separate file
