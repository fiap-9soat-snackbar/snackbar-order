package com.snackbar.basket.application.usecases;


import com.snackbar.basket.application.gateways.BasketUseCaseGateway;
import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;
import com.snackbar.productintegration.usecase.GetProductByNameUseCase;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BasketUseCase {

    private final BasketUseCaseGateway basketUseCaseGateway;
    private final GetProductByNameUseCase getProductByNameUseCase;

    public BasketUseCase(BasketUseCaseGateway basketUseCaseGateway, GetProductByNameUseCase getProductByNameUseCase) {
        this.basketUseCaseGateway = basketUseCaseGateway;
        this.getProductByNameUseCase = getProductByNameUseCase;
    }

    public Basket createBasket(Basket basket) {
        List<Item> updatedItems = basket.items().stream()
                .map(item -> {
                    var product = getProductByNameUseCase.getProductByName(item.name());
                    return new Item(
                            item.name(),
                            item.quantity(),
                            product.getPrice(),
                            product.getCookingTime()
                    );
                })
                .collect(Collectors.toList());

        BigDecimal totalPrice = updatedItems.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Basket updatedBasket = new Basket(
                basket.id(),
                basket.cpf(),
                updatedItems,
                totalPrice
        );
        return basketUseCaseGateway.createBasket(updatedBasket);
    }

    public Basket findBasket(String basketId) {
        return basketUseCaseGateway.findBasket(basketId);
    }

    public Basket addItemToBasket(String basketId, Item item) {
        return basketUseCaseGateway.addItemToBasket(basketId, item);
    }

    public Basket deleteItemToBasket(String basketId, String name) {
        return basketUseCaseGateway.deleteItemToBasket(basketId, name);
    }

    public List<Basket> findAllBaskets() {
        return basketUseCaseGateway.findAllBaskets();
    }
}
