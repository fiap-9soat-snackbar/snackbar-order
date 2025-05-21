package com.snackbar.checkout.usecase;

import com.snackbar.basket.application.usecases.BasketUseCase;
import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;
import com.snackbar.checkout.gateway.CheckoutRepository;
import com.snackbar.order.application.usecases.OrderUseCase;
import com.snackbar.order.domain.entity.Order;
import com.snackbar.order.domain.entity.OrderItem;
import com.snackbar.order.domain.valueobject.StatusOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CheckoutOrderUseCaseImplTest {

    @Mock
    private BasketUseCase basketUseCase;

    @Mock
    private OrderUseCase orderUseCase;

    @Mock
    private CheckoutRepository checkoutRepository;

    @InjectMocks
    private CheckoutOrderUseCaseImpl checkoutOrderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckoutWithNonExistentBasket() {
        String nonExistentBasketId = "non-existent-id";
        when(basketUseCase.findBasket(nonExistentBasketId)).thenThrow(new RuntimeException("Basket not found"));

        assertThrows(RuntimeException.class, () -> checkoutOrderUseCase.checkout(nonExistentBasketId));
    }

    @Test
    public void testCheckoutWithValidBasket() {
        // Arrange
        String basketId = "123";
        Basket basket = new Basket(
                basketId,
                "12345678901",
                List.of(new Item("Item 1", 2, BigDecimal.valueOf(50.00), 10)),
                BigDecimal.valueOf(100.00)
        );

        when(basketUseCase.findBasket(basketId)).thenReturn(basket);

        Order expectedOrder = new Order();
        expectedOrder.setCpf("12345678901");
        expectedOrder.setStatusOrder(StatusOrder.NOVO);
        expectedOrder.setTotalPrice(BigDecimal.valueOf(100.00));
        expectedOrder.setPaymentMethod("mercado_pago");
        expectedOrder.setItems(List.of(
                new OrderItem("Item 1", 2, BigDecimal.valueOf(50.00), 10, "Sem cebola")
        ));

        when(orderUseCase.createOrder(any(Order.class))).thenReturn(expectedOrder);

        // Act
        Order result = checkoutOrderUseCase.checkout(basketId);

        // Assert
        assertEquals(expectedOrder.getCpf(), result.getCpf());
        assertEquals(expectedOrder.getStatusOrder(), result.getStatusOrder());
        assertEquals(expectedOrder.getTotalPrice(), result.getTotalPrice());
        assertEquals(expectedOrder.getPaymentMethod(), result.getPaymentMethod());
        assertEquals(expectedOrder.getItems().size(), result.getItems().size());
        assertEquals(expectedOrder.getItems().get(0).getName(), result.getItems().get(0).getName());
        assertEquals(expectedOrder.getItems().get(0).getQuantity(), result.getItems().get(0).getQuantity());
        assertEquals(expectedOrder.getItems().get(0).getPrice(), result.getItems().get(0).getPrice());
        assertEquals(expectedOrder.getItems().get(0).getCookingTime(), result.getItems().get(0).getCookingTime());
        assertEquals(expectedOrder.getItems().get(0).getCustomization(), result.getItems().get(0).getCustomization());
    }

    @Test
    public void testCheckout_NullBasketId_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checkoutOrderUseCase.checkout(null));
    }

    @Test
    public void test_CheckoutOrderUseCaseImpl_ConstructorInitialization() {
        CheckoutOrderUseCaseImpl checkoutOrderUseCase = new CheckoutOrderUseCaseImpl(
                checkoutRepository,
                basketUseCase,
                orderUseCase
        );

        assertNotNull(checkoutOrderUseCase, "CheckoutOrderUseCaseImpl should be initialized");
    }
}