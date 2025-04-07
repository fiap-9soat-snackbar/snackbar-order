package com.snackbar.basket.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "baskets")
@Data
@AllArgsConstructor
public class BasketEntity {

    @Id
    private String id;
    private String cpf;
    private List<ItemEntity> items = new ArrayList<>();
    private BigDecimal totalPrice;
}
