package com.snackbar.checkout.usecase;

import com.snackbar.basket.application.usecases.BasketUseCase;
import com.snackbar.basket.domain.entity.Basket;
import com.snackbar.checkout.gateway.CheckoutRepository;
import com.snackbar.orderRefactory.domain.entity.Order;
import com.snackbar.orderRefactory.domain.entity.OrderItem;
import com.snackbar.orderRefactory.domain.valueobject.StatusOrder;
import com.snackbar.orderRefactory.application.usecases.OrderUseCase;
import com.snackbar.payment.application.usecases.CreatePaymentUseCase;
import com.snackbar.payment.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class CheckoutOrderUseCaseImpl implements CheckoutOrderUseCase {

    private final CheckoutRepository checkoutRepository;
    private final BasketUseCase basketUseCase;
    private final OrderUseCase orderUseCase;
    private final CreatePaymentUseCase createPaymentUseCase;

    public CheckoutOrderUseCaseImpl(
            CheckoutRepository checkoutRepository,
            BasketUseCase basketUseCase,
            OrderUseCase orderUseCase,
            CreatePaymentUseCase createPaymentUseCase
    ) {
        this.checkoutRepository = checkoutRepository;
        this.basketUseCase = basketUseCase;
        this.orderUseCase = orderUseCase;
        this.createPaymentUseCase = createPaymentUseCase;
    }

    @Override
    public Order checkout(String basketId) {
        Basket basket = basketUseCase.findBasket(basketId);

        Order order = new Order();
        order.setCpf(basket.cpf());
        order.setStatusOrder(StatusOrder.NOVO);
        order.setTotalPrice(basket.totalPrice());
        order.setPaymentMethod("mercado_pago");
        order.setItems(basket.items().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setName(item.name());
            orderItem.setQuantity(item.quantity());
            return orderItem;
        }).collect(Collectors.toList()));

        Order orderCreated = this.orderUseCase.createOrder(order);

        return orderCreated;
    }
}
