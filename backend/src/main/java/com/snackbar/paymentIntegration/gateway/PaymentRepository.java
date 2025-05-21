package com.snackbar.paymentIntegration.gateway;


import com.snackbar.checkout.presentation.CreatePaymentRequest;

public interface PaymentRepository {
    void createPayment(CreatePaymentRequest payment);
}