package com.snackbar.paymentIntegration.gateway;

import com.snackbar.checkout.presentation.CreatePaymentRequest;
import com.snackbar.paymentIntegration.common.exception.PaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentRepositoryImplTest {

    private RestTemplate restTemplate;
    private PaymentRepositoryImpl paymentRepository;

    @BeforeEach
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        paymentRepository = new PaymentRepositoryImpl(restTemplate);
        ReflectionTestUtils.setField(paymentRepository, "paymentServiceUrl", "http://localhost:8080");
    }

    @Test
    public void testCreatePayment_Successful() {
        CreatePaymentRequest paymentRequest = new CreatePaymentRequest("123", "test");

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        when(restTemplate.postForEntity(
                eq("http://localhost:8080/api/payments/mercadopago"),
                any(HttpEntity.class),
                eq(Void.class)
        )).thenReturn(responseEntity);

        assertDoesNotThrow(() -> paymentRepository.createPayment(paymentRequest));
        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(Void.class));
    }

    @Test
    public void testCreatePayment_FailureStatus() {
        CreatePaymentRequest paymentRequest = new CreatePaymentRequest("123", "test");

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(restTemplate.postForEntity(
                eq("http://localhost:8080/api/payments/mercadopago"),
                any(HttpEntity.class),
                eq(Void.class)
        )).thenReturn(responseEntity);

        PaymentException exception = assertThrows(PaymentException.class, () -> {
            paymentRepository.createPayment(paymentRequest);
        });

        assertTrue(exception.getMessage().contains("Failed to create payment"));
        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(Void.class));
    }

    @Test
    public void testCreatePayment_RestClientExceptionThrown() {
        CreatePaymentRequest paymentRequest = new CreatePaymentRequest("123", "test");

        when(restTemplate.postForEntity(
                anyString(),
                any(HttpEntity.class),
                eq(Void.class)
        )).thenThrow(new RestClientException("Connection error"));

        PaymentException exception = assertThrows(PaymentException.class, () -> {
            paymentRepository.createPayment(paymentRequest);
        });

        assertTrue(exception.getMessage().contains("Failed to process payment"));
        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(Void.class));
    }
}