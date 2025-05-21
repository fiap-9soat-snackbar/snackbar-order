package com.snackbar.paymentIntegration.usecase;

import com.snackbar.checkout.presentation.CreatePaymentRequest;

public interface PaymentUseCase {
    void createPayment(CreatePaymentRequest payment);
}