package com.snackbar.checkout.usecase;


import com.snackbar.order.domain.entity.Order;

public interface CheckoutOrderUseCase {
    Order checkout(String orderId);
}
