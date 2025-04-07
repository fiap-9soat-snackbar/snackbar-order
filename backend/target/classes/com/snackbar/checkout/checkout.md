 
## 📍Checkout Endpoints

| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /api/checkout/{orderId}</kbd>     | See [request details](#checkout)

<h3 id="checkout">POST /checkout/:orderId</h3>

**RESPONSE**  
```json
{
    "message": "Pagamento do pedido {orderId} pago via Mercado Pago (QR Code) com sucesso!"
}
```
