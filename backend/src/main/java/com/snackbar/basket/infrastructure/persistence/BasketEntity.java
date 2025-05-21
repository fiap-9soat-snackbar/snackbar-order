package com.snackbar.basket.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "baskets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketEntity {

    @Id
    private String id;
    private String cpf;
    private List<ItemEntity> items = new ArrayList<>();
    private BigDecimal totalPrice;
}
