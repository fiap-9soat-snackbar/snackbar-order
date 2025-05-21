package com.snackbar.order.infrastructure.gateways;

import com.snackbar.order.domain.entity.Order;
import com.snackbar.order.infrastructure.persistence.OrderEntity;
import com.snackbar.order.infrastructure.persistence.OrderRefactoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderRepositoryGatewayTest {

    private OrderRefactoryRepository orderRefactoryRepository;
    private OrderEntityMapper orderEntityMapper;
    private OrderRepositoryGateway repository;

    private final OrderEntity orderEntity = new OrderEntity();
    private final Order order = new Order();

    @BeforeEach
    public void setUp() {
        orderRefactoryRepository = mock(OrderRefactoryRepository.class);
        orderEntityMapper = mock(OrderEntityMapper.class);
        repository = new OrderRepositoryGateway(orderRefactoryRepository, orderEntityMapper);
    }

    @Test
    public void testCreateOrder() {
        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);
        when(orderRefactoryRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toDomainObj(orderEntity)).thenReturn(order);

        Order result = repository.createOrder(order);

        assertEquals(order, result);
    }

    @Test
    public void testUpdateOrder() {
        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);
        when(orderRefactoryRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toDomainObj(orderEntity)).thenReturn(order);

        Order result = repository.updateOrder(order);

        assertEquals(order, result);
    }

    @Test
    public void testListOrders() {
        when(orderRefactoryRepository.findAll()).thenReturn(List.of(orderEntity));
        when(orderEntityMapper.toDomainObj(orderEntity)).thenReturn(order);

        List<Order> result = repository.listOrders();

        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
    }

    @Test
    public void testSearchOrderId_found() {
        when(orderRefactoryRepository.findById("123")).thenReturn(Optional.of(orderEntity));
        when(orderEntityMapper.toDomainObj(orderEntity)).thenReturn(order);

        Order result = repository.searchOrderId("123");
        assertEquals(order, result);
    }

    @Test
    public void testSearchOrderId_notFound() {
        when(orderRefactoryRepository.findById("123")).thenReturn(Optional.empty());

        Order result = repository.searchOrderId("123");
        assertNull(result);
    }

    @Test
    public void testUpdateStatusOrder_found() {
        when(orderRefactoryRepository.findById("123")).thenReturn(Optional.of(orderEntity));

        repository.updateStatusOrder("123");

        verify(orderRefactoryRepository).save(orderEntity);
    }

    @Test
    public void testUpdateStatusOrder_notFound() {
        when(orderRefactoryRepository.findById("123")).thenReturn(Optional.empty());

        repository.updateStatusOrder("123");

        verify(orderRefactoryRepository, never()).save(any());
    }

    @Test
    public void testGetOrderByOrderNumber_found() {
        when(orderRefactoryRepository.findByOrderNumber("000001")).thenReturn(Optional.of(orderEntity));
        when(orderEntityMapper.toDomainObj(orderEntity)).thenReturn(order);

        Order result = repository.getOrderByOrderNumber("000001");

        assertEquals(order, result);
    }

    @Test
    public void testGetOrderByOrderNumber_notFound() {
        when(orderRefactoryRepository.findByOrderNumber("000001")).thenReturn(Optional.empty());

        Order result = repository.getOrderByOrderNumber("000001");

        assertNull(result);
    }

    @Test
    public void testGetSortedOrders() {
        when(orderRefactoryRepository.findAllByOrderByOrderNumberAsc()).thenReturn(List.of(orderEntity));
        when(orderEntityMapper.toDomainObj(orderEntity)).thenReturn(order);

        List<Order> result = repository.getSortedOrders();

        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
    }

    @Test
    public void testFindOrderById_found() {
        when(orderRefactoryRepository.findById("id123")).thenReturn(Optional.of(orderEntity));
        when(orderEntityMapper.toDomainObj(orderEntity)).thenReturn(order);

        Optional<Order> result = repository.findOrderById("id123");

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    public void testFindOrderById_notFound() {
        when(orderRefactoryRepository.findById("id123")).thenReturn(Optional.empty());

        Optional<Order> result = repository.findOrderById("id123");

        assertFalse(result.isPresent());
    }

    @Test
    public void testFindUserByCpf() {
        Optional<String> result = repository.findUserByCpf("cpf");
        assertTrue(result.isPresent());
    }


    @Test
    public void testFindTopByOrderByOrderNumberDesc_notFound() {
        when(orderRefactoryRepository.findTopByOrderByOrderNumberDesc()).thenReturn(Optional.empty());

        String result = repository.findTopByOrderByOrderNumberDesc();

        assertNull(result);
    }
}
