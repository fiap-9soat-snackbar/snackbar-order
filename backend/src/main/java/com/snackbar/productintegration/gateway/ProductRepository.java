package com.snackbar.productintegration.gateway;

import com.snackbar.productintegration.entity.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findByName(String name);
}