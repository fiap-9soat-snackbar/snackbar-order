package com.snackbar.basket.application.usecases;

import com.snackbar.basket.application.gateways.BasketUseCaseGateway;
import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;
import com.snackbar.productv2.application.usecases.GetProductv2ByNameUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BasketUseCase {

    private final BasketUseCaseGateway basketUseCaseGateway;
    private final GetProductv2ByNameUseCase getProductUseCase;

    public BasketUseCase(BasketUseCaseGateway basketUseCaseGateway, GetProductv2ByNameUseCase getProductUseCase) {
        this.basketUseCaseGateway = basketUseCaseGateway;
        this.getProductUseCase = getProductUseCase;
    }

    public Basket createBasket(Basket basket) {
        List<Item> updatedItems = basket.items().stream()
                .map(item -> {
                    var product = getProductUseCase.getProductv2ByName(item.name());
                    return new Item(
                            item.name(),
                            item.quantity(),
                            product.price(),
                            product.cookingTime()
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
