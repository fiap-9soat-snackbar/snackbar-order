package com.snackbar.productintegration.usecase.impl;

import com.snackbar.productintegration.dto.ProductDTO;
import com.snackbar.productintegration.common.exception.BusinessException;
import com.snackbar.productintegration.entity.Product;
import com.snackbar.productintegration.usecase.GetProductByNameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.snackbar.productintegration.gateway.ProductRepository;

@Service
public class GetProductByNameUseCaseImpl implements GetProductByNameUseCase {
    private final ProductRepository ProductRepository;

    @Autowired
    public GetProductByNameUseCaseImpl(ProductRepository ProductRepository) {
        this.ProductRepository = ProductRepository;
    }

    @Override
    public ProductDTO getProductByName(String name) {
        Product product = ProductRepository.findByName(name)
            .orElseThrow(() -> new BusinessException("Product not found with name: " + name));
        return mapToDTO(product);
    }

    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getDescription(),
                product.getPrice(),
                product.getCookingTime()
        );
    }
}