package com.snackbar.checkout.presentation;

public record CreatePaymentRequest(String orderId, String paymentMethod) {
    
}