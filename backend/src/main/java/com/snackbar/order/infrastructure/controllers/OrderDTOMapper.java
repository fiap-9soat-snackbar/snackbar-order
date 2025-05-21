package com.snackbar.order.infrastructure.controllers;

import com.snackbar.order.domain.entity.Order;
import com.snackbar.order.domain.entity.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDTOMapper {

    public Order toDomain(CreateOrderRequest request) {
        List<OrderItem> items = request.items().stream()
                .map(item -> new OrderItem(
                        item.getName(), // Add appropriate value for the name
                        item.getQuantity(),
                        item.getPrice(),
                        item.getCookingTime(),
                        item.getCustomization()
                ))
                .collect(Collectors.toList());
        return new Order(
                null, // Add appropriate value for the first argument
                null, // Add appropriate value for the second argument
                request.orderDate(),
                request.cpf(),
                request.name(), // Add appropriate value for the fifth argument
                items,
                null, // Add appropriate value for the seventh argument
                null, // Add appropriate value for the eighth argument
                request.totalPrice(),
                0  // Add appropriate value for the tenth argument
        );
    }
    public CreateOrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());
        return new CreateOrderResponse(
                order.getId(),
                order.getCpf(), // Added cpf field
                order.getOrderDateTime(),
                items,
                order.getTotalPrice()
        );
    }
}
