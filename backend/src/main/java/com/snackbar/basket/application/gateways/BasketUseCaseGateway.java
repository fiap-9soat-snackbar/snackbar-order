package com.snackbar.basket.application.gateways;

import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;

import java.util.List;

public interface BasketUseCaseGateway {
    Basket createBasket(Basket basket);
    Basket findBasket(String basketId);
    List<Basket> findAllBaskets();
    Basket addItemToBasket(String basketId, Item orderId);
    Basket deleteItemToBasket(String basketId, String itemId);
}
