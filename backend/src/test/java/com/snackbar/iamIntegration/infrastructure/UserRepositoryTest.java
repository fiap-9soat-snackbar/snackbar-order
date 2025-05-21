package com.snackbar.iamIntegration.infrastructure;

import com.snackbar.iamIntegration.domain.IamRole;
import com.snackbar.iamIntegration.domain.UserEntity;
import com.snackbar.iamIntegration.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    private UserRepository userRepository;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        restTemplate = mock(RestTemplate.class);
        ReflectionTestUtils.setField(userRepository, "managementServiceUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(userRepository, "restTemplate", restTemplate);
    }

    @Test
    void shouldReturnUserEntityWhenStatusIsOk() {
        String cpf = "12345678900";
        UserEntity mockUser = UserEntity.builder()
                .id("1")
                .name("João")
                .email("joao@email.com")
                .cpf(cpf)
                .role(IamRole.USER)
                .password("senha")
                .build();

        ResponseEntity<UserEntity> response = new ResponseEntity<>(mockUser, HttpStatus.OK);

        String expectedUrl = "http://localhost:8080/api/user/cpf/12345678900";

        when(restTemplate.getForEntity(expectedUrl, UserEntity.class)).thenReturn(response);

        Optional<UserEntity> result = userRepository.findByCpf(cpf);

        assertThat(result).isPresent();
        assertThat(result.get().getCpf()).isEqualTo(cpf);
        assertThat(result.get().getName()).isEqualTo("João");
        assertThat(result.get().getEmail()).isEqualTo("joao@email.com");
        assertThat(result.get().getRole()).isEqualTo(IamRole.USER);
    }

    @Test
    void shouldReturnEmptyWhenStatusIsNotOk() {
        String cpf = "98765432100";
        ResponseEntity<UserEntity> response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        String expectedUrl = "http://localhost:8080/api/user/cpf/98765432100";
        when(restTemplate.getForEntity(expectedUrl, UserEntity.class)).thenReturn(response);

        Optional<UserEntity> result = userRepository.findByCpf(cpf);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenRestClientFails() {
        String cpf = "00011122233";
        String expectedUrl = "http://localhost:8080/api/user/cpf/00011122233";

        when(restTemplate.getForEntity(expectedUrl, UserEntity.class))
                .thenThrow(new RestClientException("Timeout"));

        assertThatThrownBy(() -> userRepository.findByCpf(cpf))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Erro ao buscar usuário por CPF:00011122233");
    }
}
