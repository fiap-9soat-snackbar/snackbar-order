package com.snackbar.orderRefactory.infrastructure.gateways;

import com.snackbar.product.dto.ProductDTO;
import com.snackbar.orderRefactory.domain.entity.OrderItem;

public class ProductDTOToOrderItemMapper {

    public OrderItem toOrderItem(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        return new OrderItem(
                productDTO.getName(),
                0,
                productDTO.getPrice(),
                productDTO.getCookingTime(),
                ""
        );
    }
}
