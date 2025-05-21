package com.snackbar.basket.infrastructure.gateways;

import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.basket.domain.entity.Item;
import com.snackbar.basket.infrastructure.persistence.BasketEntity;
import com.snackbar.basket.infrastructure.persistence.ItemEntity;

import java.util.List;
import java.util.stream.Collectors;

public class BasketEntityMapper {

    private final ItemEntityMapper itemEntityMapper = new ItemEntityMapper();

    public BasketEntity toEntity(Basket basketDomainObj) {
        List<ItemEntity> itemEntities = basketDomainObj.items().stream()
                .map(itemEntityMapper::toEntity)
                .collect(Collectors.toList());

        return new BasketEntity(
                basketDomainObj.id(),
                basketDomainObj.cpf(),
                itemEntities,
                basketDomainObj.totalPrice()
        );
    }

    public Basket toDomainObj(BasketEntity basketEntity) {
        List<Item> items = basketEntity.getItems().stream()
                .map(itemEntityMapper::toDomainObj)
                .collect(Collectors.toList());

        return new Basket(
                basketEntity.getId(),
                basketEntity.getCpf(),
                items,
                basketEntity.getTotalPrice()
        );
    }
}
