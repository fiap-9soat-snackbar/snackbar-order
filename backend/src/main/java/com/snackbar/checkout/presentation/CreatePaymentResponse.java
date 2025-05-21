package com.snackbar.checkout.presentation;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreatePaymentResponse {
    private String id;
    private String status;
    private String externalUrl;
    private BigDecimal amount;
}