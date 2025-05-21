package com.snackbar.productintegration.usecase;

import com.snackbar.productintegration.dto.ProductDTO;

public interface GetProductByNameUseCase {
    ProductDTO getProductByName(String name);
}