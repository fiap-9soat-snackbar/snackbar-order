package com.snackbar.checkout.presentation;


import com.snackbar.checkout.usecase.CheckoutOrderUseCase;
import com.snackbar.paymentIntegration.usecase.PaymentUseCase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutOrderUseCase checkoutOrderUseCase;
    private final PaymentUseCase paymentProductUseCase;

    public CheckoutController(CheckoutOrderUseCase checkoutOrderUseCase,
                              PaymentUseCase paymentProductUseCase) {
        this.checkoutOrderUseCase = checkoutOrderUseCase;
        this.paymentProductUseCase = paymentProductUseCase;
    }
    @PostMapping("/{basketId}")
    public String makePayment(@PathVariable String basketId) {
        var order = checkoutOrderUseCase.checkout(basketId);

        CreatePaymentRequest request = new CreatePaymentRequest(order.getId(), "mercado_pago");
        this.paymentProductUseCase.createPayment(request);

        return "Pagamento do pedido " + basketId + " pago via Mercado Pago (QR Code) com sucesso!";
    }
}
