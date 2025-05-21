package com.snackbar.basket.domain.valueobject;

// This is supposed to be a Value Object

import java.util.UUID;

public record ProductId(UUID id) {

    public ProductId {
        if (id == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
    }

    public ProductId() {
        this(UUID.randomUUID());
    }
    
}
