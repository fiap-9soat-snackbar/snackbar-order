package com.snackbar.order.infrastructure.gateways;

import com.snackbar.order.application.gateways.OrderGateway;
import com.snackbar.order.domain.entity.Order;
import com.snackbar.order.infrastructure.persistence.OrderEntity;
import com.snackbar.order.infrastructure.persistence.OrderRefactoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepositoryGateway implements OrderGateway {

    private final OrderRefactoryRepository orderRefactoryRepository;
    private final OrderEntityMapper orderEntityMapper;

    public OrderRepositoryGateway(OrderRefactoryRepository orderRefactoryRepository, OrderEntityMapper orderEntityMapper) {
        this.orderRefactoryRepository = orderRefactoryRepository;
        this.orderEntityMapper = orderEntityMapper;
    }

    @Override
    public Order createOrder(Order orderDomainObj) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(orderDomainObj);
        OrderEntity savedObj = orderRefactoryRepository.save(orderEntity);
        return orderEntityMapper.toDomainObj(savedObj);
    }

    @Override
    public Order updateOrder(Order orderDomainObj) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(orderDomainObj);
        OrderEntity updatedObj = orderRefactoryRepository.save(orderEntity);
        return orderEntityMapper.toDomainObj(updatedObj);
    }

    @Override
    public List<Order> listOrders() {
        List<OrderEntity> orderEntities = orderRefactoryRepository.findAll();
        return orderEntities.stream()
                .map(orderEntityMapper::toDomainObj)
                .collect(Collectors.toList());
    }

    @Override
    public Order searchOrderId(String orderId) {
        OrderEntity orderEntity = orderRefactoryRepository.findById(orderId).orElse(null);
        return orderEntity != null ? orderEntityMapper.toDomainObj(orderEntity) : null;
    }

    @Override
    public void updateStatusOrder(String orderId) {
        OrderEntity orderEntity = orderRefactoryRepository.findById(orderId).orElse(null);
        if (orderEntity != null) {
            // Update the status of the order here
            orderRefactoryRepository.save(orderEntity);
        }
    }

    @Override
    public Order getOrderByOrderNumber(String orderNumber) {
        Optional<OrderEntity> orderEntity = orderRefactoryRepository.findByOrderNumber(orderNumber);
        return orderEntity != null ? orderEntityMapper.toDomainObj(orderEntity.orElse(null)) : null;
    }

    @Override
    public List<Order> getSortedOrders() {
        List<OrderEntity> orderEntities = orderRefactoryRepository.findAllByOrderByOrderNumberAsc();
        return orderEntities.stream()
                .map(orderEntityMapper::toDomainObj)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> findOrderById(String orderId) {
        return orderRefactoryRepository.findById(orderId)
                .map(orderEntityMapper::toDomainObj);
    }

    @Override
    public Optional<String> findUserByCpf(String cpf) {
//        return orderRepository.findUserByCpf(cpf);
        return " ".describeConstable();
    }

    @Override
    public String findTopByOrderByOrderNumberDesc() {
        return orderRefactoryRepository.findTopByOrderByOrderNumberDesc()
                .map(com.snackbar.order.domain.entity.Order::getOrderNumber)
                .orElse(null);
    }
}
