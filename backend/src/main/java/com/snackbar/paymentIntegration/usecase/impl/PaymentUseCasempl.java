package com.snackbar.paymentIntegration.usecase.impl;

import com.snackbar.checkout.presentation.CreatePaymentRequest;
import com.snackbar.paymentIntegration.gateway.PaymentRepository;
import com.snackbar.paymentIntegration.usecase.PaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentUseCasempl implements PaymentUseCase {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentUseCasempl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void createPayment(CreatePaymentRequest payment) {
        paymentRepository.createPayment(payment);
    }
}