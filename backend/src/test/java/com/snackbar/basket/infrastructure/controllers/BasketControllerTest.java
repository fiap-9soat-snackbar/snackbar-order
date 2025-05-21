package com.snackbar.basket.infrastructure.controllers;

import com.snackbar.basket.application.usecases.BasketUseCase;
import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BasketControllerTest {

    private BasketUseCase basketUseCase;
    private BasketDTOMapper basketDTOMapper;
    private BasketController controller;

    @BeforeEach
    public void setUp() {
        basketUseCase = mock(BasketUseCase.class);
        basketDTOMapper = mock(BasketDTOMapper.class);
        controller = new BasketController(basketUseCase, basketDTOMapper);
    }
    @Test
    public void testDeleteItemFromBasket() {
        String basketId = "basket123";
        String itemName = "Chips";
        Basket mockBasket = new Basket(basketId, "12345678900", List.of(new Item("item1", 2, BigDecimal.valueOf(15.99), 10)), BigDecimal.valueOf(31.98));
        CreateBasketResponse mockResponse = new CreateBasketResponse(basketId, "12345678900", List.of(new ItemResponse("item1", 2, BigDecimal.valueOf(15.99), 10)), BigDecimal.valueOf(31.98));

        when(basketUseCase.deleteItemToBasket(basketId, itemName)).thenReturn(mockBasket);
        when(basketDTOMapper.toResponse(mockBasket)).thenReturn(mockResponse);

        CreateBasketResponse result = controller.deleteItem(basketId, itemName);

        assertEquals(mockResponse, result);
        verify(basketUseCase).deleteItemToBasket(basketId, itemName);
        verify(basketDTOMapper).toResponse(mockBasket);
    }

    @Test
    public void testFindBasketById() {
        String basketId = "basket123";
        Basket mockBasket = new Basket(basketId, "12345678900", List.of(new Item("item1", 2, BigDecimal.valueOf(15.99), 10)), BigDecimal.valueOf(31.98));
        CreateBasketResponse mockResponse = new CreateBasketResponse(basketId, "12345678900", List.of(new ItemResponse("item1", 2, BigDecimal.valueOf(15.99), 10)), BigDecimal.valueOf(31.98));

        when(basketUseCase.findBasket(basketId)).thenReturn(mockBasket);
        when(basketDTOMapper.toResponse(mockBasket)).thenReturn(mockResponse);

        CreateBasketResponse result = controller.find(basketId);

        assertEquals(mockResponse, result);
        verify(basketUseCase).findBasket(basketId);
        verify(basketDTOMapper).toResponse(mockBasket);
    }

    @Test
    public void testAddItemToBasket() {
        String basketId = "basket123";
        ItemRequest itemRequest = new ItemRequest("item1", "Burger", 2, BigDecimal.valueOf(15.99), 10, "No onions");
        Item mappedItem = new Item("item1", 2, BigDecimal.valueOf(15.99), 10);
        Basket updatedBasket = new Basket(basketId, "12345678900", List.of(mappedItem), BigDecimal.valueOf(31.98));
        CreateBasketResponse mockResponse = new CreateBasketResponse(basketId, "12345678900", List.of(new ItemResponse("item1", 2, BigDecimal.valueOf(15.99), 10)), BigDecimal.valueOf(31.98));

        when(basketDTOMapper.toItem(itemRequest)).thenReturn(mappedItem);
        when(basketUseCase.addItemToBasket(basketId, mappedItem)).thenReturn(updatedBasket);
        when(basketDTOMapper.toResponse(updatedBasket)).thenReturn(mockResponse);

        CreateBasketResponse result = controller.addItem(basketId, itemRequest);

        assertEquals(mockResponse, result);
        verify(basketDTOMapper).toItem(itemRequest);
        verify(basketUseCase).addItemToBasket(basketId, mappedItem);
        verify(basketDTOMapper).toResponse(updatedBasket);
    }

    @Test
    public void testCreateBasket() {
        CreateBasketRequest request = new CreateBasketRequest(
                "basket123",
                Instant.parse("2023-01-01T10:00:00Z"),
                "12345678900",
                "John Doe",
                List.of(new ItemRequest("item1", "Burger", 2, BigDecimal.valueOf(15.99), 10, "No onions")),
                BigDecimal.valueOf(31.98)
        );

        Basket newBasket = new Basket("basket123", "12345678900", new ArrayList<>(), BigDecimal.ZERO);
        Basket createdBasket = new Basket("basket123", "12345678900", new ArrayList<>(), BigDecimal.TEN);
        CreateBasketResponse expectedResponse = new CreateBasketResponse("basket123", "12345678900", new ArrayList<>(), BigDecimal.TEN);

        when(basketDTOMapper.toBasket(request)).thenReturn(newBasket);
        when(basketUseCase.createBasket(newBasket)).thenReturn(createdBasket);
        when(basketDTOMapper.toResponse(createdBasket)).thenReturn(expectedResponse);

        CreateBasketResponse result = controller.create(request);

        assertEquals(expectedResponse, result);
        verify(basketDTOMapper).toBasket(request);
        verify(basketUseCase).createBasket(newBasket);
        verify(basketDTOMapper).toResponse(createdBasket);
    }

    @Test
    public void testFindAllBaskets() {
        List<Basket> baskets = List.of(
                new Basket("1", "123", new ArrayList<>(), BigDecimal.ZERO),
                new Basket("2", "456", new ArrayList<>(), BigDecimal.ZERO)
        );

        List<CreateBasketResponse> responses = List.of(
                new CreateBasketResponse("1", "123", new ArrayList<>(), BigDecimal.ZERO),
                new CreateBasketResponse("2", "456", new ArrayList<>(), BigDecimal.ZERO)
        );

        when(basketUseCase.findAllBaskets()).thenReturn(baskets);
        when(basketDTOMapper.toResponseList(baskets)).thenReturn(responses);

        List<CreateBasketResponse> result = controller.findAll();

        assertEquals(responses, result);
        verify(basketUseCase).findAllBaskets();
        verify(basketDTOMapper).toResponseList(baskets);
    }
}