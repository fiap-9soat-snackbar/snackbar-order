package com.snackbar.iamIntegration.application;

import com.snackbar.iamIntegration.infrastructure.UserRepository;
import com.snackbar.iamIntegration.web.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUserByCpf(String cpf) {
        return userRepository.findByCpf(cpf).map(user ->
                UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .cpf(user.getCpf())
                        .email(user.getEmail())
                        .role(user.getRole())
                    .build()
        ).orElseThrow();
    }
}
