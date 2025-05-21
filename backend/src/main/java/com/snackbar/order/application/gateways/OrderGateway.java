package com.snackbar.order.application.gateways;

import com.snackbar.order.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {
    Order createOrder(Order order);
    Order updateOrder(Order order);
    List<Order> listOrders();
    Order searchOrderId(String orderId);
    void updateStatusOrder(String orderId);
    Order getOrderByOrderNumber(String orderNumber);
    List<Order> getSortedOrders();
    Optional<Order> findOrderById(String orderId);
    Optional<String> findUserByCpf(String cpf);
    String findTopByOrderByOrderNumberDesc();
}
