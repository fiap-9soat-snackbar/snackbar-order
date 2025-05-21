package com.snackbar.order.infrastructure.controllers;

import com.snackbar.order.application.usecases.OrderUseCase;
import com.snackbar.order.domain.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateOrderControllerTest {

    private OrderUseCase orderUseCase;
    private OrderDTOMapper orderDTOMapper;
    private CreateOrderController controller;

    @BeforeEach
    public void setUp() {
        orderUseCase = mock(OrderUseCase.class);
        orderDTOMapper = mock(OrderDTOMapper.class);
        controller = new CreateOrderController(orderUseCase, orderDTOMapper);
    }

    private CreateOrderRequest buildValidRequest() {
        OrderItemRequest item = new OrderItemRequest(
                "prod123",
                "Product Name",
                2,
                new BigDecimal("10.00"),
                15,
                "No onions"
        );

        return new CreateOrderRequest(
                "cust123",
                "João da Silva",
                "12345678900",
                Instant.now(),
                List.of(item),
                new BigDecimal("20.00")
        );
    }

    @Test
    public void testCreateOrder_Success() {
        CreateOrderRequest request = buildValidRequest();
        Order order = new Order();

        when(orderDTOMapper.toDomain(request)).thenReturn(order);
        when(orderUseCase.createOrder(order)).thenReturn(order);

        Order result = controller.createOrder(request);

        assertNotNull(result);
        verify(orderUseCase).createOrder(order);
    }

    @Test
    public void testCreateOrder_ThrowsIfCpfNull() {
        CreateOrderRequest request = new CreateOrderRequest(
                "cust123",
                "João da Silva",
                null,
                Instant.now(),
                List.of(),
                BigDecimal.ZERO
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.createOrder(request);
        });

        assertEquals("CPF is mandatory for creating an order", exception.getMessage());
    }

    @Test
    public void testUpdateOrder() {
        CreateOrderRequest request = buildValidRequest();
        Order order = new Order();

        when(orderDTOMapper.toDomain(request)).thenReturn(order);
        when(orderUseCase.updateOrder(order)).thenReturn(order);

        Order result = controller.updateOrder(request);

        assertEquals(order, result);
        verify(orderUseCase).updateOrder(order);
    }

    @Test
    public void testListOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderUseCase.listOrders()).thenReturn(orders);

        List<Order> result = controller.listOrders();

        assertEquals(2, result.size());
    }

    @Test
    public void testSearchOrderId() {
        Order order = new Order();
        when(orderUseCase.searchOrderId("1")).thenReturn(order);

        Order result = controller.searchOrderId("1");

        assertEquals(order, result);
    }

    @Test
    public void testGetOrderByOrderNumber_Success() {
        Order order = new Order();
        when(orderUseCase.getOrderByOrderNumber("ORD123")).thenReturn(order);

        ResponseEntity<?> response = controller.getOrderByOrderNumber("ORD123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testGetOrderByOrderNumber_NotFound() {
        when(orderUseCase.getOrderByOrderNumber("ORD999"))
                .thenThrow(new IllegalArgumentException("Order not found"));

        ResponseEntity<?> response = controller.getOrderByOrderNumber("ORD999");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order not found", response.getBody());
    }

    @Test
    public void testGetSortedOrders() {
        List<Order> sortedOrders = Arrays.asList(new Order(), new Order());
        when(orderUseCase.getSortedOrders()).thenReturn(sortedOrders);

        List<Order> result = controller.getSortedOrders();

        assertEquals(2, result.size());
    }

    @Test
    public void testUpdateStatusOrder() {
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest("1", "COMPLETED");
        Order order = new Order();

        when(orderUseCase.updateStatusOrder("1", "COMPLETED")).thenReturn(order);

        Order result = controller.updateStatusOrder(request);

        assertEquals(order, result);
    }

    @Test
    public void testUpdateOrderStatus_Success() {
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest("1", "COMPLETED");
        Order order = new Order();

        when(orderUseCase.updateStatusOrder("1", "COMPLETED")).thenReturn(order);

        ResponseEntity<?> response = controller.updateOrderStatus("1", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testUpdateOrderStatus_Failure() {
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest("1", "INVALID_STATUS");

        when(orderUseCase.updateStatusOrder("1", "INVALID_STATUS"))
                .thenThrow(new IllegalArgumentException("Invalid status"));

        ResponseEntity<?> response = controller.updateOrderStatus("1", request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid status", response.getBody());
    }
}
