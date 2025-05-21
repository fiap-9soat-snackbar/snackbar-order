package com.snackbar.order.infrastructure.controllers;

import com.snackbar.order.application.usecases.OrderUseCase;
import com.snackbar.order.domain.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordersrefactored")
public class CreateOrderController {

    private final OrderUseCase orderUseCase;
    private final OrderDTOMapper orderDTOMapper;

    public CreateOrderController(OrderUseCase orderUseCase, OrderDTOMapper orderDTOMapper) {
        this.orderUseCase = orderUseCase;
        this.orderDTOMapper = orderDTOMapper;
    }

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        if (request.cpf() == null) {
            throw new IllegalArgumentException("CPF is mandatory for creating an order");
        }
        Order order = orderDTOMapper.toDomain(request);
        return orderUseCase.createOrder(order);
    }

    @PutMapping
    public Order updateOrder(@RequestBody CreateOrderRequest request) {
        Order order = orderDTOMapper.toDomain(request);
        return orderUseCase.updateOrder(order);
    }

    @GetMapping
    public List<Order> listOrders() {
        return orderUseCase.listOrders();
    }

    @GetMapping("/{id}")
    public Order searchOrderId(@PathVariable String id) {
        return orderUseCase.searchOrderId(id);
    }

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<?> getOrderByOrderNumber(@PathVariable String orderNumber) {
        try {
            Order order = orderUseCase.getOrderByOrderNumber(orderNumber);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/sorted")
    public List<Order> getSortedOrders() {
        return orderUseCase.getSortedOrders();
    }

    @PutMapping("/status")
    public Order updateStatusOrder(@RequestBody UpdateOrderStatusRequest request) {
        String status = request.status();
        String id = request.orderId();
        return orderUseCase.updateStatusOrder(id, status);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @RequestBody UpdateOrderStatusRequest request) {
        try {
            Order order = orderUseCase.updateStatusOrder(id, request.status());
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
