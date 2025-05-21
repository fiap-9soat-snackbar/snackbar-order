package com.snackbar.paymentIntegration.usecase.impl;

import com.snackbar.checkout.presentation.CreatePaymentRequest;
import com.snackbar.paymentIntegration.gateway.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaymentUseCasemplTest {

    @Test
    public void testPaymentUseCasemplWithNullRepository() {
        PaymentUseCasempl paymentUseCaseImpl = new PaymentUseCasempl(null);
        CreatePaymentRequest paymentRequest = new CreatePaymentRequest("1", "test");

        assertThrows(NullPointerException.class, () -> {
            paymentUseCaseImpl.createPayment(paymentRequest);
        });
    }

    @Test
    public void test_PaymentUseCasempl_ConstructorInjection() {
        PaymentRepository mockRepository = mock(PaymentRepository.class);
        PaymentUseCasempl paymentUseCase = new PaymentUseCasempl(mockRepository);
    }

    @Test
    public void test_createPayment_delegatesToRepository() {
        PaymentRepository mockRepository = mock(PaymentRepository.class);
        PaymentUseCasempl paymentUseCase = new PaymentUseCasempl(mockRepository);
        CreatePaymentRequest testPayment = new CreatePaymentRequest("1", "test");

        paymentUseCase.createPayment(testPayment);

        verify(mockRepository).createPayment(testPayment);
    }
}
