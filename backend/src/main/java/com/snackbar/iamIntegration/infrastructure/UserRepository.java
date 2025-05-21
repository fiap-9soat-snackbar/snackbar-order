package com.snackbar.iamIntegration.infrastructure;

import com.snackbar.iamIntegration.domain.UserEntity;
import com.snackbar.iamIntegration.infrastructure.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Repository
public class UserRepository {

    @Value("${management.service.url}")
    private String managementServiceUrl;

    private RestTemplate restTemplate;

    public Optional<UserEntity> findByCpf(String cpf) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(managementServiceUrl)
                    .path("/api/user/cpf/{cpf}")
                    .buildAndExpand(cpf)
                    .toUriString();

            ResponseEntity<UserEntity> response = restTemplate.getForEntity(url, UserEntity.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return Optional.ofNullable(response.getBody());
            }

            return Optional.empty();
        } catch (RestClientException e) {
            throw new UserNotFoundException("Erro ao buscar usuário por CPF:" + cpf, e);
        }
    }
}
