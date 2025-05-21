package com.snackbar.basket.infrastructure.gateways;

import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;
import com.snackbar.basket.infrastructure.persistence.BasketEntity;
import com.snackbar.basket.infrastructure.persistence.BasketRepository;
import com.snackbar.basket.infrastructure.persistence.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketRepositoryGatewayTest {

    @Mock
    private BasketEntityMapper basketEntityMapper;

    @Mock
    private BasketRepository basketRepository;

    @InjectMocks
    private BasketRepositoryGateway basketRepositoryGateway;

    @Mock
    private ItemEntityMapper itemEntityMapper;

    @BeforeEach
    void setUp() {
        // Não é necessário chamar MockitoAnnotations.openMocks(this) com @ExtendWith(MockitoExtension.class)
    }

    @Test
    public void testAddItemToBasket_BasketNotFound() {
        String nonExistentBasketId = "nonExistentBasketId";
        Item item = new Item("item1", 1, BigDecimal.TEN, 5);

        when(basketRepository.findById(nonExistentBasketId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> basketRepositoryGateway.addItemToBasket(nonExistentBasketId, item));
        assertEquals("Basket not found", exception.getMessage());
    }

    @Test
    public void test_BasketRepositoryGateway_ConstructorInitialization() {
        BasketRepository mockBasketRepository = mock(BasketRepository.class);
        BasketEntityMapper mockBasketEntityMapper = mock(BasketEntityMapper.class);
        ItemEntityMapper mockItemEntityMapper = mock(ItemEntityMapper.class);

        BasketRepositoryGateway gateway = new BasketRepositoryGateway(
                mockBasketRepository, mockBasketEntityMapper, mockItemEntityMapper);

        assertNotNull(gateway, "BasketRepositoryGateway should be initialized");
    }

    @Test
    public void test_addItemToBasket_addsItemToExistingBasket() {
        String basketId = "basket123";
        Item item = new Item("item1", 1, BigDecimal.TEN, 5);
        BasketEntity existingBasketEntity = new BasketEntity();
        existingBasketEntity.setItems(new ArrayList<>());

        ItemEntity itemEntity = new ItemEntity("item1", 1, BigDecimal.TEN, 5);        BasketEntity updatedBasketEntity = new BasketEntity();
        updatedBasketEntity.setItems(new ArrayList<>(List.of(itemEntity)));

        Basket expectedBasket = new Basket("basket123", "12345678901", List.of(item), BigDecimal.TEN);

        when(basketRepository.findById(basketId)).thenReturn(Optional.of(existingBasketEntity));
        when(itemEntityMapper.toEntity(item)).thenReturn(itemEntity);
        when(basketRepository.save(existingBasketEntity)).thenReturn(updatedBasketEntity);
        when(basketEntityMapper.toDomainObj(updatedBasketEntity)).thenReturn(expectedBasket);

        Basket result = basketRepositoryGateway.addItemToBasket(basketId, item);

        assertEquals(expectedBasket, result);
    }

    @Test
    public void test_createBasket_SuccessfulCreation() {
        Basket inputBasket = new Basket("basket123", "12345678901", List.of(), BigDecimal.ZERO);
        BasketEntity inputEntity = new BasketEntity();
        BasketEntity savedEntity = new BasketEntity();
        Basket expectedBasket = new Basket("basket123", "12345678901", List.of(), BigDecimal.ZERO);

        when(basketEntityMapper.toEntity(inputBasket)).thenReturn(inputEntity);
        when(basketRepository.save(inputEntity)).thenReturn(savedEntity);
        when(basketEntityMapper.toDomainObj(savedEntity)).thenReturn(expectedBasket);

        Basket result = basketRepositoryGateway.createBasket(inputBasket);

        assertEquals(expectedBasket, result);
        verify(basketEntityMapper).toEntity(inputBasket);
        verify(basketRepository).save(inputEntity);
        verify(basketEntityMapper).toDomainObj(savedEntity);
    }

    @Test
    public void test_deleteItemToBasket_basketNotFound() {
        String basketId = "non-existent-basket-id";
        when(basketRepository.findById(basketId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> basketRepositoryGateway.deleteItemToBasket(basketId, "item-name"));
        assertEquals("Basket not found", exception.getMessage());

        verify(basketRepository).findById(basketId);
    }

    @Test
    public void test_deleteItemToBasket_whenBasketExistsAndItemIsDeleted() {
        String basketId = "test-basket-id";
        String itemName = "Test Item";
        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setItems(new ArrayList<>());
        BasketEntity updatedEntity = new BasketEntity();
        Basket expectedBasket = new Basket("test-basket-id", "12345678901", List.of(), BigDecimal.ZERO);

        when(basketRepository.findById(basketId)).thenReturn(Optional.of(basketEntity));
        when(basketRepository.save(basketEntity)).thenReturn(updatedEntity);
        when(basketEntityMapper.toDomainObj(updatedEntity)).thenReturn(expectedBasket);

        Basket result = basketRepositoryGateway.deleteItemToBasket(basketId, itemName);

        verify(basketRepository).findById(basketId);
        verify(basketRepository).save(basketEntity);
        verify(basketEntityMapper).toDomainObj(updatedEntity);
        assertEquals(expectedBasket, result);
    }

    @Test
    public void test_findAllBaskets_emptyRepository() {
        when(basketRepository.findAll()).thenReturn(Collections.emptyList());

        List<Basket> result = basketRepositoryGateway.findAllBaskets();

        assertTrue(result.isEmpty(), "Expected an empty list when repository is empty");
    }

    @Test
    public void test_findAllBaskets_returnsAllMappedBaskets() {
        BasketEntity basketEntity1 = new BasketEntity();
        BasketEntity basketEntity2 = new BasketEntity();
        List<BasketEntity> basketEntities = Arrays.asList(basketEntity1, basketEntity2);

        Basket basket1 = new Basket("basket1", "12345678901", List.of(), BigDecimal.ZERO);
        Basket basket2 = new Basket("basket2", "12345678902", List.of(), BigDecimal.ZERO);

        when(basketRepository.findAll()).thenReturn(basketEntities);
        when(basketEntityMapper.toDomainObj(any(BasketEntity.class))).thenAnswer(invocation -> {
            BasketEntity arg = invocation.getArgument(0);
            if (arg == basketEntity1) return basket1;
            if (arg == basketEntity2) return basket2;
            return null;
        });

        List<Basket> result = basketRepositoryGateway.findAllBaskets();

        assertEquals(2, result.size());
        assertTrue(result.contains(basket1));
        assertTrue(result.contains(basket2));
        verify(basketRepository, times(1)).findAll();
        verify(basketEntityMapper, times(2)).toDomainObj(any(BasketEntity.class));
    }

    @Test
    public void test_findBasket_basketNotFound() {
        String basketId = "nonexistent-basket-id";
        when(basketRepository.findById(basketId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> basketRepositoryGateway.findBasket(basketId));
        assertEquals("Basket not found", exception.getMessage());
    }

    @Test
    public void test_findBasket_whenBasketExists() {
        String basketId = "test-basket-id";
        BasketEntity basketEntity = new BasketEntity();
        Basket expectedBasket = new Basket("test-basket-id", "12345678901", List.of(), BigDecimal.ZERO);

        when(basketRepository.findById(basketId)).thenReturn(Optional.of(basketEntity));
        when(basketEntityMapper.toDomainObj(basketEntity)).thenReturn(expectedBasket);

        Basket result = basketRepositoryGateway.findBasket(basketId);

        assertNotNull(result);
        assertEquals(expectedBasket, result);
        verify(basketRepository).findById(basketId);
        verify(basketEntityMapper).toDomainObj(basketEntity);
    }
}