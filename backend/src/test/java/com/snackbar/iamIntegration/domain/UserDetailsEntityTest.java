package com.snackbar.iamIntegration.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsEntityTest {

    private UserDetailsEntity userDetails;

    @BeforeEach
    void setup() {
        userDetails = UserDetailsEntity.builder()
                .id("001")
                .name("João")
                .email("joao@email.com")
                .cpf("12345678901")
                .password("senhaSegura")
                .role(IamRole.USER)
                .build();
    }

    @Test
    void testBuilderAndGetters() {
        assertThat(userDetails.getId()).isEqualTo("001");
        assertThat(userDetails.getName()).isEqualTo("João");
        assertThat(userDetails.getEmail()).isEqualTo("joao@email.com");
        assertThat(userDetails.getCpf()).isEqualTo("12345678901");
        assertThat(userDetails.getPassword()).isEqualTo("senhaSegura");
        assertThat(userDetails.getRole()).isEqualTo(IamRole.USER);
    }

    @Test
    void testSetters() {
        userDetails.setName("Maria");
        userDetails.setEmail("maria@email.com");
        userDetails.setCpf("10987654321");
        userDetails.setPassword("novaSenha");
        userDetails.setRole(IamRole.CONSUMER);

        assertThat(userDetails.getName()).isEqualTo("Maria");
        assertThat(userDetails.getEmail()).isEqualTo("maria@email.com");
        assertThat(userDetails.getCpf()).isEqualTo("10987654321");
        assertThat(userDetails.getPassword()).isEqualTo("novaSenha");
        assertThat(userDetails.getRole()).isEqualTo(IamRole.CONSUMER);
    }

    @Test
    void testUserDetailsInterfaceMethods() {
        Collection<?> authorities = userDetails.getAuthorities();
        assertThat(authorities).isEmpty();

        assertThat(userDetails.getUsername()).isEqualTo("12345678901");
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testPrivateGetAuthoritiesByRoleMethod() throws Exception {
        Method method = UserDetailsEntity.class.getDeclaredMethod("getAuthorities", IamRole.class);
        method.setAccessible(true);

        String result = (String) method.invoke(userDetails, IamRole.CONSUMER);
        assertEquals("CONSUMER", result);
    }

    @Test
    void testNoArgsConstructor() {
        UserDetailsEntity user = new UserDetailsEntity();
        assertNotNull(user);
    }

    @Test
    void testAllArgsConstructor() {
        UserDetailsEntity user = new UserDetailsEntity("1", "Ana", "ana@email.com", "11122233344", IamRole.USER, "pass");
        assertEquals("1", user.getId());
        assertEquals("Ana", user.getName());
        assertEquals("ana@email.com", user.getEmail());
        assertEquals("11122233344", user.getCpf());
        assertEquals(IamRole.USER, user.getRole());
        assertEquals("pass", user.getPassword());
    }
}

