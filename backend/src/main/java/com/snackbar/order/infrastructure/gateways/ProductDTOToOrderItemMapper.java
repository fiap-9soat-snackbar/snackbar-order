package com.snackbar.order.infrastructure.gateways;

import com.snackbar.order.domain.entity.OrderItem;
import com.snackbar.productintegration.dto.ProductDTO;

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
