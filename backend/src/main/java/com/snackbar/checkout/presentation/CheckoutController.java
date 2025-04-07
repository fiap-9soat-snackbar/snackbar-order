package com.snackbar.checkout.presentation;

import com.snackbar.checkout.usecase.CheckoutOrderUseCase;
import com.snackbar.payment.domain.entity.Payment;
import com.snackbar.payment.infrastructure.controllers.CreatePaymentRequest;
import com.snackbar.payment.infrastructure.controllers.PaymentController;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutOrderUseCase checkoutOrderUseCase;
    private final PaymentController paymentController;

    public CheckoutController(CheckoutOrderUseCase checkoutOrderUseCase, PaymentController paymentController) {
        this.checkoutOrderUseCase = checkoutOrderUseCase;
        this.paymentController = paymentController;
    }
    @PostMapping("/{basketId}")
    public String makePayment(@PathVariable String basketId) {
        var order = checkoutOrderUseCase.checkout(basketId);

        CreatePaymentRequest request = new CreatePaymentRequest(order.getId(), "mercado_pago");
        this.paymentController.createPayment(request);

        return "Pagamento do pedido " + basketId + " pago via Mercado Pago (QR Code) com sucesso!";
    }
}
