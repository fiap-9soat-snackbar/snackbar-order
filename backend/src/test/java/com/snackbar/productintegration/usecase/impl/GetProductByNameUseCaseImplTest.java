package com.snackbar.productintegration.usecase.impl;

import com.snackbar.productintegration.common.exception.BusinessException;
import com.snackbar.productintegration.dto.ProductDTO;
import com.snackbar.productintegration.entity.Product;
import com.snackbar.productintegration.gateway.ProductRepository;
import com.snackbar.productintegration.usecase.GetProductByNameUseCase;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProductByNameUseCaseImplTest {

    @InjectMocks
    private GetProductByNameUseCaseImpl getProductByNameUseCase;

    @Mock
    private ProductRepository productRepository;

    /**
     * Tests the scenario where a product with the given name does not exist.
     * This should result in a BusinessException being thrown.
     */
    @Test
    public void testGetProductByName_ProductNotFound() {
        String nonExistentProductName = "NonExistentProduct";
        when(productRepository.findByName(nonExistentProductName)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> getProductByNameUseCase.getProductByName(nonExistentProductName));
    }

    /**
     * Test case for getProductByName method when the product is not found.
     * This test verifies that a BusinessException is thrown when trying to retrieve a non-existent product.
     */
    @Test
    public void testGetProductByName_ProductNotFound_2() {
        String nonExistentProductName = "NonExistentProduct";
        when(productRepository.findByName(nonExistentProductName)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> getProductByNameUseCase.getProductByName(nonExistentProductName));
    }

    /**
     * Test case for GetProductByNameUseCaseImpl constructor
     * Verifies that the ProductRepository is correctly injected and the use case can retrieve a product by name
     */
    @Test
    public void test_getProductByNameUseCaseImpl_constructor() {
        // Arrange
        String productName = "Test Product";
        Product mockProduct = new Product(
                "1", // id as String instead of Long
                productName,
                "Category",
                "Description",
                BigDecimal.valueOf(10.0), // price as BigDecimal instead of double
                15 // cookingTime as Integer
        );
        when(productRepository.findByName(productName)).thenReturn(Optional.of(mockProduct));

        // Act
        ProductDTO result = getProductByNameUseCase.getProductByName(productName);

        // Assert
        assertEquals(productName, result.getName());
        assertEquals(1L, Long.valueOf(result.getId()));
        assertEquals("Category", result.getCategory());
        assertEquals("Description", result.getDescription());
        assertEquals(BigDecimal.valueOf(10.0), result.getPrice());
        assertEquals(15, result.getCookingTime());
    }

    /**
     * Test case for getProductByName method when the product is found.
     * It verifies that the method returns the correct ProductDTO when a product with the given name exists.
     */
    @Test
    public void test_getProductByName_whenProductExists() {
        // Arrange
        String productName = "Test Product";
        Product product = new Product(
                "1", // id as String instead of Long
                productName,
                "Category",
                "Description",
                BigDecimal.valueOf(10.0), // price as BigDecimal instead of double
                15 // cookingTime as Integer
        );
        when(productRepository.findByName(productName)).thenReturn(Optional.of(product));

        // Act
        ProductDTO result = getProductByNameUseCase.getProductByName(productName);

        // Assert
        assertEquals(1L, Long.valueOf(result.getId()));
        assertEquals(productName, result.getName());
        assertEquals("Category", result.getCategory());
        assertEquals("Description", result.getDescription());
        assertEquals(BigDecimal.valueOf(10.0), result.getPrice());
        assertEquals(15, result.getCookingTime());
    }

}
