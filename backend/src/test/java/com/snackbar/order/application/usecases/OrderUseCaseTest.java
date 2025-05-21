package com.snackbar.order.application.usecases;

import com.snackbar.iamIntegration.application.UserService;
import com.snackbar.iamIntegration.web.dto.UserResponse;
import com.snackbar.order.application.gateways.OrderGateway;
import com.snackbar.order.application.gateways.ProductGateway;
import com.snackbar.order.domain.entity.Order;
import com.snackbar.order.domain.entity.OrderItem;
import com.snackbar.order.domain.valueobject.StatusOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderUseCaseTest {

    private OrderGateway orderGateway;
    private ProductGateway productGateway;
    private UserService userService;
    private OrderUseCase orderUseCase;

    @BeforeEach
    public void setup() {
        orderGateway = mock(OrderGateway.class);
        productGateway = mock(ProductGateway.class);
        userService = mock(UserService.class);
        orderUseCase = new OrderUseCase(orderGateway, productGateway, userService);
    }

    @Test
    public void testCreateOrder_Success() {
        OrderItem item = new OrderItem("Product A", 2, new BigDecimal("5.00"), 10, "Extra");
        Order order = new Order();
        order.setCpf("12345678900");
        order.setItems(List.of(item));

        UserResponse mockUser = UserResponse.builder()
                .id("id")
                .name("João")
                .cpf("12345678900")
                .email("joao@email.com")
                .build();

        when(userService.getUserByCpf("12345678900")).thenReturn(mockUser);
        when(orderGateway.findTopByOrderByOrderNumberDesc()).thenReturn("999");
        when(productGateway.getProductByName("Product A"))
                .thenReturn(new OrderItem("Product A", 2, new BigDecimal("5.00"), 10, "Extra"));
        when(orderGateway.createOrder(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderUseCase.createOrder(order);

        assertEquals("João", result.getName());
        assertEquals(StatusOrder.NOVO, result.getStatusOrder());
        assertEquals(new BigDecimal("10.00"), result.getTotalPrice());
        assertEquals(1, result.getItems().size());
    }

    @Test
    public void testCreateOrder_WhenCpfIsNull_ShouldThrowException() {
        Order order = new Order();
        order.setCpf(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderUseCase.createOrder(order);
        });

        assertEquals("CPF is mandatory for creating an order", exception.getMessage());
    }

    @Test
    public void testListOrders_ReturnsAllOrders() {
        List<Order> mockOrders = List.of(new Order(), new Order());
        when(orderGateway.listOrders()).thenReturn(mockOrders);

        List<Order> result = orderUseCase.listOrders();

        assertEquals(mockOrders, result);
        verify(orderGateway).listOrders();
    }

    @Test
    public void testSearchOrderId_ReturnsOrder() {
        Order mockOrder = new Order();
        when(orderGateway.searchOrderId("order123")).thenReturn(mockOrder);

        Order result = orderUseCase.searchOrderId("order123");

        assertEquals(mockOrder, result);
        verify(orderGateway).searchOrderId("order123");
    }

    @Test
    public void testGetOrderByOrderNumber_ReturnsOrder() {
        Order mockOrder = new Order();
        when(orderGateway.getOrderByOrderNumber("ORD1001")).thenReturn(mockOrder);

        Order result = orderUseCase.getOrderByOrderNumber("ORD1001");

        assertEquals(mockOrder, result);
        verify(orderGateway).getOrderByOrderNumber("ORD1001");
    }

    @Test
    public void testGetSortedOrders_FiltersAndSortsCorrectly() {
        Order o1 = new Order();
        o1.setStatusOrder(StatusOrder.PREPARACAO);
        o1.setOrderDateTime(Instant.parse("2023-05-01T10:00:00Z"));

        Order o2 = new Order();
        o2.setStatusOrder(StatusOrder.PRONTO);
        o2.setOrderDateTime(Instant.parse("2023-05-01T09:00:00Z"));

        Order o3 = new Order();
        o3.setStatusOrder(StatusOrder.RECEBIDO);
        o3.setOrderDateTime(Instant.parse("2023-05-01T11:00:00Z"));

        Order o4 = new Order(); // This one should be ignored
        o4.setStatusOrder(StatusOrder.CANCELADO);
        o4.setOrderDateTime(Instant.parse("2023-05-01T12:00:00Z"));

        List<Order> mockOrders = List.of(o1, o2, o3, o4);
        when(orderGateway.getSortedOrders()).thenReturn(mockOrders);

        List<Order> result = orderUseCase.getSortedOrders();

        assertEquals(3, result.size());
        assertTrue(result.contains(o1));
        assertTrue(result.contains(o2));
        assertTrue(result.contains(o3));
        assertFalse(result.contains(o4));
    }

    @Test
    public void testCompareStatus_AllScenarios() {
        assertEquals(0, invokeCompare(StatusOrder.PRONTO, StatusOrder.PRONTO));
        assertEquals(-1, invokeCompare(StatusOrder.PRONTO, StatusOrder.PREPARACAO));
        assertEquals(1, invokeCompare(StatusOrder.PREPARACAO, StatusOrder.PRONTO));
        assertEquals(-1, invokeCompare(StatusOrder.PREPARACAO, StatusOrder.RECEBIDO));
        assertEquals(1, invokeCompare(StatusOrder.RECEBIDO, StatusOrder.PREPARACAO));
        assertEquals(0, invokeCompare(StatusOrder.RECEBIDO, StatusOrder.RECEBIDO));
    }

    // helper para acessar método private
    private int invokeCompare(StatusOrder s1, StatusOrder s2) {
        try {
            var method = OrderUseCase.class.getDeclaredMethod("compareStatus", StatusOrder.class, StatusOrder.class);
            method.setAccessible(true);
            return (int) method.invoke(orderUseCase, s1, s2);
        } catch (Exception e) {
            fail("Erro ao invocar compareStatus: " + e.getMessage());
            return 0;
        }
    }

    @Test
    public void testCreateOrder_ThrowsWhenProductNotFound() {
        OrderItem item = new OrderItem("Unknown Product", 1, BigDecimal.TEN, 10, "");
        Order order = new Order();
        order.setCpf("12345678900");
        order.setItems(List.of(item));

        UserResponse mockUser = UserResponse.builder()
                .id("id")
                .name("João")
                .cpf("12345678900")
                .email("joao@email.com")
                .build();

        when(userService.getUserByCpf("12345678900")).thenReturn(mockUser);
        when(orderGateway.findTopByOrderByOrderNumberDesc()).thenReturn("999");
        when(productGateway.getProductByName("Unknown Product")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderUseCase.createOrder(order);
        });

        assertEquals("Product not found: Unknown Product", exception.getMessage());
    }

    @Test
    public void testUpdateOrder_Success() {
        OrderItem item = new OrderItem("Product A", 1, null, 10, "");
        Order order = new Order();
        order.setId("order123");
        order.setItems(List.of(item));

        Order existingOrder = new Order();
        existingOrder.setId("order123");

        when(orderGateway.findOrderById("order123")).thenReturn(Optional.of(existingOrder));
        when(productGateway.getProductByName("Product A"))
                .thenReturn(new OrderItem("Product A", 1, new BigDecimal("7.00"), 10, ""));
        when(orderGateway.updateOrder(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderUseCase.updateOrder(order);

        assertEquals(new BigDecimal("7.00"), result.getTotalPrice());
    }

    @Test
    public void testUpdateStatusOrder_InvalidStatus() {
        Order order = new Order();
        order.setId("order123");

        when(orderGateway.findOrderById("order123")).thenReturn(Optional.of(order));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderUseCase.updateStatusOrder("order123", "INVALID");
        });

        assertEquals("Invalid order status: INVALID", exception.getMessage());
    }

    @Test
    public void testUpdateStatusOrder_Success() {
        Order order = new Order();
        order.setId("order123");

        when(orderGateway.findOrderById("order123")).thenReturn(Optional.of(order));
        when(orderGateway.updateOrder(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderUseCase.updateStatusOrder("order123", "PREPARACAO");

        assertEquals(StatusOrder.PREPARACAO, result.getStatusOrder());
    }
}
