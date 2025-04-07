package com.snackbar.checkout.usecase;

import com.snackbar.orderRefactory.domain.entity.Order;

public interface CheckoutOrderUseCase {
    Order checkout(String orderId);
}
