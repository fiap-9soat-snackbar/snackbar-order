package com.snackbar.basket.infrastructure.gateways;

import com.snackbar.basket.domain.entity.Item;
import com.snackbar.basket.infrastructure.persistence.ItemEntity;

public class ItemEntityMapper {
    public ItemEntity toEntity(Item itemDomainObj) {
        return new ItemEntity(
                itemDomainObj.name(),
                itemDomainObj.quantity(),
                itemDomainObj.price(),
                itemDomainObj.cookingTime()
        );
    }

    public Item toDomainObj(ItemEntity itemEntity) {
        return new Item(
                itemEntity.getName(),
                itemEntity.getQuantity(),
                itemEntity.getPrice(),
                itemEntity.getCookingTime()
        );
    }
}
