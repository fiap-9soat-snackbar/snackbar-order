package com.snackbar.basket.infrastructure.controllers;

import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;

import java.util.List;
import java.util.stream.Collectors;

public class BasketDTOMapper {

    public Basket toBasket(CreateBasketRequest request) {
        List<Item> items = request.items().stream()
                .map(this::toItem)
                .collect(Collectors.toList());
        return new Basket(
                request.id(),
                request.cpf(),
                items,
                request.totalPrice()
        );
    }

    public CreateBasketResponse toResponse(Basket basket) {
        List<ItemResponse> items = basket.items().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());
        return new CreateBasketResponse(
                basket.id(),
                basket.cpf(),
                items,
                basket.totalPrice()
        );
    }

    public List<CreateBasketResponse> toResponseList(List<Basket> baskets) {
        return baskets.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Item toItem(ItemRequest request) {
        return new Item(
                request.name(),
                request.quantity(),
                request.price(),
                request.cookingTime()
        );
    }

    private ItemResponse toItemResponse(Item item) {
        return new ItemResponse(
                item.name(),
                item.quantity(),
                item.price(),
                item.cookingTime()
        );
    }
}
