package com.snackbar.iamIntegration.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class UserEntity {
    private String id;
    
    private String name;

    private String email;

    private String cpf;

    private IamRole role;

    private String password;

}
