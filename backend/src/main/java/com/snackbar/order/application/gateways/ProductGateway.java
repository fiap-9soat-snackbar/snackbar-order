package com.snackbar.order.application.gateways;

import com.snackbar.order.domain.entity.OrderItem;

public interface ProductGateway {
    OrderItem getProductByName(String name);
}
