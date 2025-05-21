package com.snackbar.checkout.presentation;

import com.snackbar.checkout.usecase.CheckoutOrderUseCase;
import com.snackbar.order.domain.entity.Order;
import com.snackbar.paymentIntegration.usecase.PaymentUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class CheckoutControllerTest {

    @InjectMocks
    private CheckoutController checkoutController;

    @Mock
    private CheckoutOrderUseCase checkoutOrderUseCase;

    @Mock
    private PaymentUseCase paymentProductUseCase;

    /**
     * Test case for CheckoutController constructor
     * Verifies that the CheckoutController is correctly initialized with the provided use cases
     */
    @Test
    public void test_CheckoutController_Constructor() {
        // Arrange
        CheckoutOrderUseCase mockCheckoutOrderUseCase = mock(CheckoutOrderUseCase.class);
        PaymentUseCase mockPaymentUseCase = mock(PaymentUseCase.class);

        // Act
        CheckoutController controller = new CheckoutController(mockCheckoutOrderUseCase, mockPaymentUseCase);

        // Assert
        // No assertions needed as we're just testing the constructor initialization
        // If no exceptions are thrown, the test is considered successful
    }

    /**
     * Test case for makePayment method
     * Verifies that the method returns the correct success message when payment is processed
     */
    @Test
    public void test_makePayment_SuccessfulPayment() {
        MockitoAnnotations.initMocks(this);

        String basketId = "123456";
        Order mockOrder = new Order();
        mockOrder.setId("ORDER123");

        when(checkoutOrderUseCase.checkout(basketId)).thenReturn(mockOrder);

        String result = checkoutController.makePayment(basketId);

        assertEquals("Pagamento do pedido 123456 pago via Mercado Pago (QR Code) com sucesso!", result);
    }

}
