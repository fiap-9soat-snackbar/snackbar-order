    package com.snackbar.productintegration.gateway;

    import com.snackbar.productintegration.entity.Product;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.junit.jupiter.MockitoExtension;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.test.util.ReflectionTestUtils;
    import org.springframework.web.client.RestTemplate;
    import org.springframework.web.util.UriComponentsBuilder;

    import java.net.URI;
    import java.util.Optional;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;
    @ExtendWith(MockitoExtension.class)
    public class ProductRepositoryImplTest {
        /**
         * Tests the findByName method when the response status is OK.
         * Verifies that the method returns an Optional containing the product from the response body.
         */
        @Test
        public void testFindByNameReturnsProductWhenStatusIsOk() {
            // Arrange
            RestTemplate restTemplate = mock(RestTemplate.class);
            ProductRepositoryImpl productRepository = new ProductRepositoryImpl(restTemplate);

            // Mockar URL via reflexão
            ReflectionTestUtils.setField(productRepository, "managementServiceUrl", "http://localhost:8080");

            String productName = "TestProduct";
            Product expectedProduct = new Product();
            expectedProduct.setName(productName);

            URI expectedUri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080")
                    .path("/api/product/name/{name}")
                    .buildAndExpand(productName)
                    .toUri();

            // Corrigir aqui: toString() para bater com a chamada real
            when(restTemplate.getForEntity(expectedUri.toString(), Product.class))
                    .thenReturn(new ResponseEntity<>(expectedProduct, HttpStatus.OK));

            // Act
            Optional<Product> result = productRepository.findByName(productName);

            // Assert
            assertTrue(result.isPresent());
            assertEquals(expectedProduct, result.get());
            verify(restTemplate).getForEntity(expectedUri.toString(), Product.class);
        }


        /**
         * Tests the constructor of ProductRepositoryImpl with a null RestTemplate.
         * This is a negative test case to ensure the constructor handles null input properly.
         */
        @Test
        public void testProductRepositoryImplConstructorWithNullRestTemplate() {
            try {
                new ProductRepositoryImpl(null);
            } catch (IllegalArgumentException e) {
                // Assert that an IllegalArgumentException is thrown when a null RestTemplate is provided
                assert (e.getMessage().contains("RestTemplate cannot be null"));
            }
        }

        /**
         * Test case for the constructor of ProductRepositoryImpl
         * Verifies that a ProductRepositoryImpl instance can be created with a RestTemplate
         */
        @Test
        public void test_ProductRepositoryImpl_Constructor() {
            RestTemplate restTemplate = new RestTemplate();
            ProductRepositoryImpl productRepository = new ProductRepositoryImpl(restTemplate);
            assertNotNull(productRepository, "ProductRepositoryImpl should be created successfully");
        }

        /**
         * Tests the findByName method when the response status is not OK.
         * Verifies that an empty Optional is returned when the API response is not successful.
         */
        @Test
        public void test_findByName_whenResponseIsNotOk_returnsEmptyOptional() {
            // Arrange
            RestTemplate restTemplate = mock(RestTemplate.class);
            ProductRepositoryImpl productRepository = new ProductRepositoryImpl(restTemplate);

            // Mockar URL via reflexão
            ReflectionTestUtils.setField(productRepository, "managementServiceUrl", "http://localhost:8080");

            String productName = "NonExistentProduct";
            URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080")
                    .path("/api/product/name/{name}")
                    .buildAndExpand(productName)
                    .toUri();

            ResponseEntity<Product> mockResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

            // Mocka corretamente com .toString() para bater com a chamada real
            when(restTemplate.getForEntity(uri.toString(), Product.class)).thenReturn(mockResponse);

            // Act
            Optional<Product> result = productRepository.findByName(productName);

            // Assert
            assertTrue(result.isEmpty());
            verify(restTemplate).getForEntity(uri.toString(), Product.class);
        }
    }