package com.snackbar.basket.application.usecases;

import com.snackbar.basket.application.gateways.BasketUseCaseGateway;
import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;
import com.snackbar.productintegration.dto.ProductDTO;
import com.snackbar.productintegration.entity.Product;
import com.snackbar.productintegration.usecase.GetProductByNameUseCase;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class BasketUseCaseTest {

    /**
     * Test case for addItemToBasket method
     * Verifies that the method correctly delegates to the basketUseCaseGateway
     * and returns the result from the gateway.
     */
    @Test
    public void testAddItemToBasketDelegatesCorrectly() {
        // Arrange
        BasketUseCaseGateway mockGateway = mock(BasketUseCaseGateway.class);
        BasketUseCase basketUseCase = new BasketUseCase(mockGateway, null);
        String basketId = "test-basket-id";
        Item item = new Item("Test Item", 1, null, null);
        Basket expectedBasket = new Basket(basketId, null, null, null);

        when(mockGateway.addItemToBasket(basketId, item)).thenReturn(expectedBasket);

        // Act
        Basket result = basketUseCase.addItemToBasket(basketId, item);

        // Assert
        verify(mockGateway).addItemToBasket(basketId, item);
        assert(result == expectedBasket);
    }

    /**
     * Tests the constructor of BasketUseCase to ensure it correctly initializes
     * the basketUseCaseGateway and getProductByNameUseCase dependencies.
     */
    @Test
    public void testBasketUseCaseConstructor() {
        BasketUseCaseGateway mockBasketUseCaseGateway = mock(BasketUseCaseGateway.class);
        GetProductByNameUseCase mockGetProductByNameUseCase = mock(GetProductByNameUseCase.class);

        BasketUseCase basketUseCase = new BasketUseCase(mockBasketUseCaseGateway, mockGetProductByNameUseCase);

        // No assertions needed as we're just testing that the constructor doesn't throw an exception
    }

    /**
     * Tests the BasketUseCase constructor with null parameters.
     * This test verifies that the constructor throws a NullPointerException
     * when either of its parameters (basketUseCaseGateway or getProductByNameUseCase) is null.
     */
    /**
     * Test case for findBasket method of BasketUseCase
     * Verifies that the method correctly delegates to the basketUseCaseGateway
     * and returns the result from the gateway.
     */
    @Test
    public void testFindBasketDelegation() {
        // Arrange
        BasketUseCaseGateway mockGateway = mock(BasketUseCaseGateway.class);
        GetProductByNameUseCase mockGetProductUseCase = mock(GetProductByNameUseCase.class);
        BasketUseCase basketUseCase = new BasketUseCase(mockGateway, mockGetProductUseCase);

        String testBasketId = "test-basket-id";
        Basket expectedBasket = mock(Basket.class);

        when(mockGateway.findBasket(testBasketId)).thenReturn(expectedBasket);

        // Act
        Basket result = basketUseCase.findBasket(testBasketId);

        // Assert
        verify(mockGateway).findBasket(testBasketId);
        assert result == expectedBasket;
    }

    /**
     * Tests the behavior of findBasket method when given a null basketId.
     * This test verifies that the method properly handles null input by delegating
     * to the basketUseCaseGateway, which is expected to handle the null case.
     */
    @Test
    public void testFindBasketWithNullId() {
        BasketUseCaseGateway mockGateway = mock(BasketUseCaseGateway.class);
        when(mockGateway.findBasket(null)).thenReturn(null);

        BasketUseCase basketUseCase = new BasketUseCase(mockGateway, null);

        Basket result = basketUseCase.findBasket(null);

        assertNull(result);
        verify(mockGateway).findBasket(null);
    }

    /**
     * Tests the addItemToBasket method with null basketId.
     * This test verifies that the method properly handles a null basketId
     * by delegating to the basketUseCaseGateway.
     */
    @Test
    public void test_addItemToBasket_nullBasketId() {
        BasketUseCaseGateway mockGateway = new BasketUseCaseGateway() {
            @Override
            public Basket addItemToBasket(String basketId, Item item) {
                // Simulate the gateway's behavior for null basketId
                if (basketId == null) {
                    return null;
                }
                return new Basket(basketId, "123", null, null);
            }

            // Other methods of the interface are not implemented for this test
            @Override
            public Basket createBasket(Basket basket) {
                return null;
            }

            @Override
            public Basket findBasket(String basketId) {
                return null;
            }

            @Override
            public Basket deleteItemToBasket(String basketId, String name) {
                return null;
            }

            @Override
            public List<Basket> findAllBaskets() {
                return null;
            }
        };

        GetProductByNameUseCase mockProductUseCase = name -> null;
        BasketUseCase basketUseCase = new BasketUseCase(mockGateway, mockProductUseCase);

        Item testItem = new Item("TestItem", 1, new BigDecimal("10.00"), 5);
        Basket result = basketUseCase.addItemToBasket(null, testItem);

        assertNull(result);
    }

    /**
     * Tests the addItemToBasket method with null item.
     * This test verifies that the method properly handles a null item
     * by delegating to the basketUseCaseGateway.
     */
    @Test
    public void test_addItemToBasket_nullItem() {
        BasketUseCaseGateway mockGateway = new BasketUseCaseGateway() {
            @Override
            public Basket addItemToBasket(String basketId, Item item) {
                // Simulate the gateway's behavior for null item
                if (item == null) {
                    return null;
                }
                return new Basket(basketId, "123", null, null);
            }

            // Other methods of the interface are not implemented for this test
            @Override
            public Basket createBasket(Basket basket) {
                return null;
            }

            @Override
            public Basket findBasket(String basketId) {
                return null;
            }

            @Override
            public Basket deleteItemToBasket(String basketId, String name) {
                return null;
            }

            @Override
            public List<Basket> findAllBaskets() {
                return null;
            }
        };

        GetProductByNameUseCase mockProductUseCase = name -> null;
        BasketUseCase basketUseCase = new BasketUseCase(mockGateway, mockProductUseCase);

        Basket result = basketUseCase.addItemToBasket("basket1", null);

        assertNull(result);
    }

    /**
     * Tests the createBasket method of BasketUseCase.
     * Verifies that the method correctly creates a new basket with updated items,
     * including product details fetched from GetProductByNameUseCase.
     */
    @Test
    public void test_createBasket_updatesItemsWithProductDetails() {
        // Mock dependencies
        BasketUseCaseGateway basketUseCaseGateway = mock(BasketUseCaseGateway.class);
        GetProductByNameUseCase getProductByNameUseCase = mock(GetProductByNameUseCase.class);

        // Create BasketUseCase instance
        BasketUseCase basketUseCase = new BasketUseCase(basketUseCaseGateway, getProductByNameUseCase);

        // Prepare test data
        Item inputItem = new Item("TestProduct", 2, BigDecimal.ZERO, 0);
        Basket inputBasket = new Basket("1", "123456789", Arrays.asList(inputItem), BigDecimal.ZERO);

        // Mock product details
        ProductDTO mockProduct = new ProductDTO("1", "TestProduct", "CategoriaTeste", "Descrição do produto", BigDecimal.TEN, 5);
        when(getProductByNameUseCase.getProductByName("TestProduct")).thenReturn(mockProduct);

        // Mock createBasket method of basketUseCaseGateway
        Basket expectedBasket = new Basket("1", "123456789", 
            Arrays.asList(new Item("TestProduct", 2, BigDecimal.TEN, 5)), 
            BigDecimal.valueOf(20));
        when(basketUseCaseGateway.createBasket(any(Basket.class))).thenReturn(expectedBasket);

        // Execute the method under test
        Basket result = basketUseCase.createBasket(inputBasket);

        // Verify the result
        assertNotNull(result);
        assertEquals(expectedBasket.id(), result.id());
        assertEquals(expectedBasket.cpf(), result.cpf());
        assertEquals(expectedBasket.totalPrice(), result.totalPrice());
        assertEquals(1, result.items().size());

        Item resultItem = result.items().get(0);
        assertEquals("TestProduct", resultItem.name());
        assertEquals(2, resultItem.quantity());
        assertEquals(BigDecimal.TEN, resultItem.price());
        assertEquals(5, resultItem.cookingTime());

        // Verify interactions
        verify(getProductByNameUseCase).getProductByName("TestProduct");
        verify(basketUseCaseGateway).createBasket(any(Basket.class));
    }

    /**
     * Test case for deleteItemToBasket method
     * Verifies that the method correctly delegates to the basketUseCaseGateway
     * and returns the result from the gateway.
     */
    @Test
    public void test_deleteItemFromBasket_DelegatesAndReturnsGatewayResult() {
        // Arrange
        BasketUseCaseGateway mockGateway = Mockito.mock(BasketUseCaseGateway.class);
        BasketUseCase basketUseCase = new BasketUseCase(mockGateway, null);
        String basketId = "testBasketId";
        String itemName = "testItemName";
        Basket expectedBasket = new Basket(basketId, null, null, null);

        Mockito.when(mockGateway.deleteItemToBasket(basketId, itemName)).thenReturn(expectedBasket);

        // Act
        Basket result = basketUseCase.deleteItemToBasket(basketId, itemName);

        // Assert
        Mockito.verify(mockGateway).deleteItemToBasket(basketId, itemName);
        org.junit.jupiter.api.Assertions.assertEquals(expectedBasket, result);
    }

    /**
     * Test deleting an item from a basket with an empty basket ID.
     * This test verifies that the method throws an IllegalArgumentException
     * when an empty basket ID is provided.
     */
    @Test
    public void test_deleteItemToBasket_emptyBasketId() {
        BasketUseCaseGateway mockGateway = Mockito.mock(BasketUseCaseGateway.class);
        GetProductByNameUseCase mockGetProductUseCase = Mockito.mock(GetProductByNameUseCase.class);
        BasketUseCase basketUseCase = new BasketUseCase(mockGateway, mockGetProductUseCase);

        when(mockGateway.deleteItemToBasket("", "itemName"))
            .thenThrow(new IllegalArgumentException("Basket ID cannot be empty"));

        assertThrows(IllegalArgumentException.class, () -> {
            basketUseCase.deleteItemToBasket("", "itemName");
        });
    }

    /**
     * Test deleting an item from a basket with an empty item name.
     * This test verifies that the method throws an IllegalArgumentException
     * when an empty item name is provided.
     */
    @Test
    public void test_deleteItemToBasket_emptyItemName() {
        BasketUseCaseGateway mockGateway = Mockito.mock(BasketUseCaseGateway.class);
        GetProductByNameUseCase mockGetProductUseCase = Mockito.mock(GetProductByNameUseCase.class);
        BasketUseCase basketUseCase = new BasketUseCase(mockGateway, mockGetProductUseCase);

        when(mockGateway.deleteItemToBasket("basketId", ""))
            .thenThrow(new IllegalArgumentException("Item name cannot be empty"));

        assertThrows(IllegalArgumentException.class, () -> {
            basketUseCase.deleteItemToBasket("basketId", "");
        });
    }

    /**
     * Test case for findAllBaskets method
     * Verifies that the method correctly returns all baskets from the gateway
     */
    @Test
    public void test_findAllBaskets_returnsAllBasketsFromGateway() {
        // Arrange
        BasketUseCaseGateway mockGateway = Mockito.mock(BasketUseCaseGateway.class);
        GetProductByNameUseCase mockProductUseCase = Mockito.mock(GetProductByNameUseCase.class);
        BasketUseCase basketUseCase = new BasketUseCase(mockGateway, mockProductUseCase);

        List<Basket> expectedBaskets = Arrays.asList(
            new Basket("1", "123.456.789-00", null, null),
            new Basket("2", "987.654.321-00", null, null)
        );
        when(mockGateway.findAllBaskets()).thenReturn(expectedBaskets);

        // Act
        List<Basket> actualBaskets = basketUseCase.findAllBaskets();

        // Assert
        assertEquals(expectedBaskets, actualBaskets);
    }

    /**
     * Tests the findAllBaskets method when the gateway returns an empty list.
     * This is a negative test case to verify the behavior when no baskets are found.
     */
    @Test
    public void test_findAllBaskets_returnsEmptyList() {
        // Arrange
        BasketUseCaseGateway mockGateway = mock(BasketUseCaseGateway.class);
        when(mockGateway.findAllBaskets()).thenReturn(List.of());
        BasketUseCase basketUseCase = new BasketUseCase(mockGateway, null);

        // Act
        List<Basket> result = basketUseCase.findAllBaskets();

        // Assert
        assertTrue(result.isEmpty(), "Expected an empty list when no baskets are found");
        verify(mockGateway).findAllBaskets();
    }

}
