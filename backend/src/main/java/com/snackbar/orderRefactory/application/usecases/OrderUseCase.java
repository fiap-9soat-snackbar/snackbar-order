package com.snackbar.orderRefactory.application.usecases;

import com.snackbar.checkout.usecase.CheckoutOrderUseCaseImpl;
import com.snackbar.iam.application.UserService;
import com.snackbar.orderRefactory.application.gateways.ProductGateway;
import com.snackbar.orderRefactory.domain.entity.Order;
import com.snackbar.orderRefactory.domain.entity.OrderItem;
import com.snackbar.orderRefactory.domain.valueobject.StatusOrder;
import com.snackbar.orderRefactory.application.gateways.OrderGateway;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.usecase.IsReadyPickupUseCaseImpl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class OrderUseCase {

    private final OrderGateway orderGateway;
    private final ProductGateway productGateway;
    private final UserService userService;

    public OrderUseCase(
            OrderGateway orderGateway,
            ProductGateway productGateway,
            UserService userService
    ) {
        this.orderGateway = orderGateway;
        this.productGateway = productGateway;
        this.userService = userService;
    }

    public Order createOrder(Order order) {
        if (order.getCpf() == null) {
            throw new IllegalArgumentException("CPF is mandatory for creating an order");
        }

        // Validate if the user exists and get the user's name

        var user = this.userService.getUserByCpf(order.getCpf());

        order.setName(user.getName());

        String lastOrderNumber = orderGateway.findTopByOrderByOrderNumberDesc();
        order.setOrderNumber(Order.generateOrderNumber(lastOrderNumber));
        order.setOrderDateTime(Instant.now());
        order.setStatusOrder(StatusOrder.NOVO);

        List<OrderItem> updatedItems = order.getItems().stream().map(item -> {
            OrderItem updatedItem = productGateway.getProductByName(item.getName());
            if (updatedItem != null) {
                return new OrderItem(updatedItem.getName(), item.getQuantity(), updatedItem.getPrice(), updatedItem.getCookingTime(), updatedItem.getCustomization());
            } else {
                throw new IllegalArgumentException("Product not found: " + item.getName());
            }
        }).collect(Collectors.toList());

        order.setItems(updatedItems);

        BigDecimal totalPrice = updatedItems.stream()
                .map(item -> item.getPrice() != null ? item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);

        return orderGateway.createOrder(order);
    }

    public Order updateOrder(Order order) {
        Order existingOrder = orderGateway.findOrderById(order.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        List<OrderItem> updatedItems = order.getItems().stream().map(item -> {
            OrderItem updatedItem = productGateway.getProductByName(item.getName());
            if (updatedItem != null) {
                return new OrderItem( updatedItem.getName(), item.getQuantity(), updatedItem.getPrice(), updatedItem.getCookingTime(), updatedItem.getCustomization());
            } else {
                throw new IllegalArgumentException("Product not found: " + item.getName());
            }
        }).collect(Collectors.toList());

        existingOrder.setItems(updatedItems);

        BigDecimal totalPrice = updatedItems.stream()
                .map(item -> item.getPrice() != null ? item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        existingOrder.setTotalPrice(totalPrice);

        return orderGateway.updateOrder(existingOrder);
    }

    public List<Order> listOrders() {
        return orderGateway.listOrders();
    }

    public Order searchOrderId(String orderId) {
        return orderGateway.searchOrderId(orderId);
    }

    public Order getOrderByOrderNumber(String orderNumber) {
        return orderGateway.getOrderByOrderNumber(orderNumber);
    }

    public List<Order> getSortedOrders() {
        return orderGateway.getSortedOrders().stream()
                .filter(order -> order.getStatusOrder() == StatusOrder.PRONTO ||
                        order.getStatusOrder() == StatusOrder.PREPARACAO ||
                        order.getStatusOrder() == StatusOrder.RECEBIDO)
                .sorted((o1, o2) -> {
                    int statusComparison = compareStatus(o1.getStatusOrder(), o2.getStatusOrder());
                    if (statusComparison != 0) {
                        return statusComparison;
                    }
                    return o1.getOrderDateTime().compareTo(o2.getOrderDateTime());
                })
                .collect(Collectors.toList());
    }

    private int compareStatus(StatusOrder status1, StatusOrder status2) {
        if (status1 == status2) return 0;
        if (status1 == StatusOrder.PRONTO) return -1;
        if (status2 == StatusOrder.PRONTO) return 1;
        if (status1 == StatusOrder.PREPARACAO) return -1;
        if (status2 == StatusOrder.PREPARACAO) return 1;
        return 0;
    }

    public Order updateStatusOrder(String orderId, String orderStatus) {
        Order order = orderGateway.findOrderById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        try {
            StatusOrder status = StatusOrder.valueOf(orderStatus);
            order.setStatusOrder(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + orderStatus);
        }

       return orderGateway.updateOrder(order);
    }
}
