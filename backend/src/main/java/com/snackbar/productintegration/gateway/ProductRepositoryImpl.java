package com.snackbar.productintegration.gateway;


import com.snackbar.productintegration.entity.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final RestTemplate restTemplate;

    @Value("${management.service.url}")
    private String managementServiceUrl;

    public ProductRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Product> findByName(String name) {

        try {
            String url = UriComponentsBuilder.fromHttpUrl(managementServiceUrl)
                    .path("/api/product/name/{name}")
                    .buildAndExpand(name)
                    .toUriString();

            ResponseEntity<Product> response = restTemplate.getForEntity(url, Product.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return Optional.ofNullable(response.getBody());
            }

            return Optional.empty();
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}