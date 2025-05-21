package com.snackbar.basket.infrastructure.controllers;

import com.snackbar.basket.application.usecases.BasketUseCase;
import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    private final BasketUseCase basketUseCase;
    private final BasketDTOMapper basketDTOMapper;

    public BasketController(
            BasketUseCase basketUseCase,
            BasketDTOMapper basketDTOMapper) {
        this.basketUseCase = basketUseCase;
        this.basketDTOMapper = basketDTOMapper;
    }

    @PostMapping
    public CreateBasketResponse create(@RequestBody CreateBasketRequest request) {
        Basket basket = basketDTOMapper.toBasket(request);
        Basket createdBasket = basketUseCase.createBasket(basket);
        return basketDTOMapper.toResponse(createdBasket);
    }

    @GetMapping("/{basketId}")
    public CreateBasketResponse find(@PathVariable String basketId) {
        Basket basket = basketUseCase.findBasket(basketId);
        return basketDTOMapper.toResponse(basket);
    }

    @GetMapping("/")
    public List<CreateBasketResponse> findAll() {
        List<Basket> basket = basketUseCase.findAllBaskets();
        return basketDTOMapper.toResponseList(basket);
    }

    @PostMapping("/{basketId}/items")
    public CreateBasketResponse addItem(@PathVariable String basketId, @RequestBody ItemRequest itemRequest) {
        Item item = basketDTOMapper.toItem(itemRequest);
        Basket basket = basketUseCase.addItemToBasket(basketId, item);
        return basketDTOMapper.toResponse(basket);
    }

    @DeleteMapping("/{basketId}/items/{name}")
    public CreateBasketResponse deleteItem(@PathVariable String basketId, @PathVariable String name) {
        Basket basket = basketUseCase.deleteItemToBasket(basketId, name);
        return basketDTOMapper.toResponse(basket);
    }
}
