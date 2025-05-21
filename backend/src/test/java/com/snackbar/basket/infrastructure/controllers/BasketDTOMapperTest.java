package com.snackbar.basket.infrastructure.controllers;

import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketDTOMapperTest {

    private BasketDTOMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = new BasketDTOMapper();
    }

    @Test
    public void testToBasket() {
        ItemRequest itemRequest = new ItemRequest("item-id", "Burger", 2, BigDecimal.valueOf(15.99), 10, "No onions");
        CreateBasketRequest request = new CreateBasketRequest(
                "basket123",
                java.time.Instant.now(),
                "12345678900",
                "John Doe",
                List.of(itemRequest),
                BigDecimal.valueOf(31.98)
        );

        Basket result = mapper.toBasket(request);

        assertEquals("basket123", result.id());
        assertEquals("12345678900", result.cpf());
        assertEquals(1, result.items().size());
        assertEquals(BigDecimal.valueOf(31.98), result.totalPrice());
    }

    @Test
    public void testToResponse() {
        Item item = new Item("Burger", 2, BigDecimal.valueOf(15.99), 10);
        Basket basket = new Basket("basket123", "12345678900", List.of(item), BigDecimal.valueOf(31.98));

        CreateBasketResponse response = mapper.toResponse(basket);

        assertEquals("basket123", response.id());
        assertEquals("12345678900", response.cpf());
        assertEquals(1, response.items().size());
        assertEquals(BigDecimal.valueOf(31.98), response.totalPrice());
    }

    @Test
    public void testToResponseList() {
        Item item1 = new Item("Burger", 2, BigDecimal.valueOf(15.99), 10);
        Item item2 = new Item("Fries", 1, BigDecimal.valueOf(9.99), 5);

        Basket basket1 = new Basket("1", "11111111111", List.of(item1), BigDecimal.valueOf(31.98));
        Basket basket2 = new Basket("2", "22222222222", List.of(item2), BigDecimal.valueOf(9.99));

        List<CreateBasketResponse> responses = mapper.toResponseList(List.of(basket1, basket2));

        assertEquals(2, responses.size());
        assertEquals("1", responses.get(0).id());
        assertEquals("2", responses.get(1).id());
    }

    @Test
    public void testToItem() {
        ItemRequest request = new ItemRequest("item-id", "Burger", 2, BigDecimal.valueOf(15.99), 10, "No onions");

        Item result = mapper.toItem(request);

        assertEquals("Burger", result.name());
        assertEquals(2, result.quantity());
        assertEquals(BigDecimal.valueOf(15.99), result.price());
        assertEquals(10, result.cookingTime());
    }

    @Test
    public void testToItemResponse() {
        Item item = new Item("Burger", 2, BigDecimal.valueOf(15.99), 10);

        var response = mapper.toResponse(new Basket("id", "cpf", List.of(item), BigDecimal.TEN)).items().get(0);

        assertEquals("Burger", response.name());
        assertEquals(2, response.quantity());
        assertEquals(BigDecimal.valueOf(15.99), response.price());
        assertEquals(10, response.cookingTime());
    }
}

