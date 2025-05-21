package com.snackbar.paymentIntegration.gateway;

import com.snackbar.checkout.presentation.CreatePaymentRequest;
import com.snackbar.paymentIntegration.common.exception.PaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final RestTemplate restTemplate;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    public PaymentRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void createPayment(CreatePaymentRequest payment) {

        try {
            String url = UriComponentsBuilder.fromHttpUrl(paymentServiceUrl)
                    .path("/api/payments/mercadopago")
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreatePaymentRequest> request = new HttpEntity<>(payment, headers);

            ResponseEntity<Void> response = restTemplate.postForEntity(
                    url,
                    request,
                    Void.class
            );

            if (response.getStatusCode() != HttpStatus.CREATED) {
                throw new PaymentException("Failed to create payment. Status: " + response.getStatusCode());
            }

        } catch (RestClientException e) {
            throw new PaymentException("Failed to process payment", e);
        }
    }

}
