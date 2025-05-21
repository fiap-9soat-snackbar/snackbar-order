package com.snackbar.iamIntegration.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IamRoleTest {

    @Test
    public void testEnumValues() {
        IamRole[] roles = IamRole.values();
        assertEquals(2, roles.length);
        assertTrue(java.util.Arrays.asList(roles).contains(IamRole.CONSUMER));
        assertTrue(java.util.Arrays.asList(roles).contains(IamRole.USER));
    }

    @Test
    public void testValueOf() {
        assertEquals(IamRole.CONSUMER, IamRole.valueOf("CONSUMER"));
        assertEquals(IamRole.USER, IamRole.valueOf("USER"));
    }
}