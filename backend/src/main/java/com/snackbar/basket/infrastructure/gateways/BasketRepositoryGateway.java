package com.snackbar.basket.infrastructure.gateways;

import com.snackbar.basket.infrastructure.persistence.BasketEntity;
import com.snackbar.basket.infrastructure.persistence.BasketRepository;
import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;
import com.snackbar.basket.application.gateways.BasketUseCaseGateway;

import java.util.List;

public class BasketRepositoryGateway implements BasketUseCaseGateway {

    private final BasketRepository basketRepository;
    private final BasketEntityMapper basketEntityMapper;
    private final ItemEntityMapper itemEntityMapper;

    public BasketRepositoryGateway(BasketRepository basketRepository, BasketEntityMapper basketEntityMapper, ItemEntityMapper itemEntityMapper) {
        this.basketRepository = basketRepository;
        this.basketEntityMapper = basketEntityMapper;
        this.itemEntityMapper = itemEntityMapper;
    }

    @Override
    public Basket createBasket(Basket basketDomainObj) {
        BasketEntity basketEntity = basketEntityMapper.toEntity(basketDomainObj);
        BasketEntity savedObj = basketRepository.save(basketEntity);
        return basketEntityMapper.toDomainObj(savedObj);
    }

    @Override
    public Basket findBasket(String basketId) {
        BasketEntity basketEntity = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));
        return basketEntityMapper.toDomainObj(basketEntity);
    }

    @Override
    public List<Basket> findAllBaskets() {
        return basketRepository.findAll().stream().map(basketEntityMapper::toDomainObj).toList();
    }

    @Override
    public Basket addItemToBasket(String basketId, Item item) {
        BasketEntity basketEntity = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));
        basketEntity.getItems().add(itemEntityMapper.toEntity(item));
        BasketEntity updatedEntity = basketRepository.save(basketEntity);
        return basketEntityMapper.toDomainObj(updatedEntity);
    }

    @Override
    public Basket deleteItemToBasket(String basketId, String name) {
        BasketEntity basketEntity = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));
        basketEntity.getItems().removeIf(item -> item.getName().equals(name));
        BasketEntity updatedEntity = basketRepository.save(basketEntity);
        return basketEntityMapper.toDomainObj(updatedEntity);
    }
}
