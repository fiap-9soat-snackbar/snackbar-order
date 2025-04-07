package com.snackbar.orderRefactory.domain.entity;

import com.snackbar.orderRefactory.domain.valueobject.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor

public class Order {
    private String id;
    private String orderNumber;
    private Instant orderDateTime;
    private String cpf;
    private String name;
    private List<OrderItem> items;
    private StatusOrder statusOrder;
    private String paymentMethod;
    private BigDecimal totalPrice;
    private long remainingTime;

    public Order(String id, String orderNumber, Instant orderDateTime, String cpf, String name, List<OrderItem> items, StatusOrder statusOrder, String paymentMethod, BigDecimal totalPrice, long remainingTime) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDateTime = orderDateTime;
        this.cpf = cpf;
        this.name = name;
        this.items = items;
        this.statusOrder = statusOrder;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.remainingTime = remainingTime;
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }

    public long calculateRemainingTime() {
        Instant now = Instant.now();
        long elapsedTime = java.time.Duration.between(orderDateTime, now).toMinutes();
        return Math.max(0, getWaitingTime() - elapsedTime);
    }

    public int getWaitingTime() {
        return items.stream()
                .mapToInt(item -> item.getCookingTime() * item.getQuantity())
                .sum();
    }

    public static String generateOrderNumber(String lastOrderNumber) {
        if (lastOrderNumber == null || lastOrderNumber.isEmpty()) {
            return "000001";
        }
        int nextNumber = Integer.parseInt(lastOrderNumber) + 1;
        return String.format("%06d", nextNumber);
    }
}
