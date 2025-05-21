package com.snackbar.order.domain.entity;

import com.snackbar.order.domain.valueobject.StatusOrder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void testGetWaitingTime() {
        OrderItem item1 = new OrderItem("Product A", 2, new BigDecimal("5.00"), 5, "Extra");
        OrderItem item2 = new OrderItem("Product B", 1, new BigDecimal("3.00"), 10, "");
        Order order = new Order();
        order.setItems(List.of(item1, item2));

        assertEquals(20, order.getWaitingTime());
    }

    @Test
    public void testCalculateRemainingTime() {
        OrderItem item = new OrderItem("Product A", 1, new BigDecimal("5.00"), 10, "");
        Order order = new Order();
        order.setItems(List.of(item));
        order.setOrderDateTime(Instant.now().minusSeconds(300)); // 5 minutos atrás

        long remaining = order.calculateRemainingTime();
        assertTrue(remaining <= 10 && remaining >= 4); // algum valor entre 4~10
    }

    @Test
    public void testGenerateOrderNumber_WhenNullOrEmpty() {
        assertEquals("000001", Order.generateOrderNumber(null));
        assertEquals("000001", Order.generateOrderNumber(""));
    }

    @Test
    public void testGenerateOrderNumber_WhenValid() {
        assertEquals("000123", Order.generateOrderNumber("122"));
        assertEquals("001000", Order.generateOrderNumber("999"));
    }

    @Test
    public void testGettersAndSetters() {
        OrderItem item = new OrderItem("Product A", 1, new BigDecimal("5.00"), 10, "");
        Order order = new Order();
        order.setId("id");
        order.setOrderNumber("123");
        order.setOrderDateTime(Instant.now());
        order.setCpf("cpf");
        order.setName("name");
        order.setItems(List.of(item));
        order.setStatusOrder(StatusOrder.NOVO);
        order.setPaymentMethod("PIX");
        order.setTotalPrice(new BigDecimal("20.00"));
        order.setRemainingTime(30);

        assertEquals("id", order.getId());
        assertEquals("123", order.getOrderNumber());
        assertEquals("cpf", order.getCpf());
        assertEquals("name", order.getName());
        assertEquals(List.of(item), order.getItems());
        assertEquals(StatusOrder.NOVO, order.getStatusOrder());
        assertEquals("PIX", order.getPaymentMethod());
        assertEquals(new BigDecimal("20.00"), order.getTotalPrice());
        assertEquals(30, order.getRemainingTime());
    }
}