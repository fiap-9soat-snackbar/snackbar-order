package com.snackbar.orderRefactory.application.gateways;

import com.snackbar.orderRefactory.domain.entity.OrderItem;
import com.snackbar.product.dto.ProductDTO;

public interface ProductGateway {
    OrderItem getProductByName(String name);
}
