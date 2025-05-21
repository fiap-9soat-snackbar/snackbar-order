 
## 📍OrdersRefactored Endpoints

| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /api/ordersrefactored</kbd>     | See [request details](#get-ordersrefactored)
| <kbd>GET /api/ordersrefactored/{id}</kbd>     |  See [request details](#get-ordersrefactored-id)
| <kbd>GET /api/ordersrefactored/number/{orderNumber}</kbd>     |See [request details](#get-ordersrefactored-ordernumber)
| <kbd>GET /api/ordersrefactored/sorted</kbd>     | See [request details](#get-ordersrefactored-sorted)
| <kbd>POST /api/ordersrefactored</kbd>     | See [request details](#post-ordersrefactored)
| <kbd>PUT /api/ordersrefactored/{id}</kbd>     | See [request details](#put-ordersrefactored)
| <kbd>PUT /api/ordersrefactored/status</kbd>     | See [request details](#put-ordersrefactored-status)

<h3 id="get-ordersrefactored">GET /api/ordersrefactored</h3>

**RESPONSE**  
```json
[
    {
        "id": "672662025524c80e99fe6911",
        "orderNumber": "000001",
        "orderDateTime": "2024-10-31T04:52:17.367Z",
        "cpf": "12345",
        "name": "Jose Moreira",
        "itemEntities": [
            {
                "name": "Hot Dog de Salsicha",
                "quantity": 1,
                "price": 20,
                "cookingTime": 10,
                "customization": "Molho extra"
            }
        ],
        "statusOrder": "NOVO",
        "paymentMethod": null,
        "totalPrice": 20,
        "remainingTime": 0,
        "waitingTime": 10
    }
    /* All other orders */
]
```

<h3 id="get-ordersrefactored-id">GET /api/ordersrefactored/{id}</h3>

**RESPONSE**
```json
{
    "id": "672662025524c80e99fe6911",
    "orderNumber": "000001",
    "orderDateTime": "2024-10-31T04:52:17.367Z",
    "cpf": "12345",
    "name": "Jose Moreira",
    "itemEntities": [
        {
            "name": "Hot Dog de Salsicha",
            "quantity": 1,
            "price": 20,
            "cookingTime": 10,
            "customization": "Molho extra"
        }
    ],
    "statusOrder": "NOVO",
    "paymentMethod": null,
    "totalPrice": 20,
    "remainingTime": 0,
    "waitingTime": 10
}
```

<h3 id="get-ordersrefactored-ordernumber">GET /api/ordersrefactored/number/{orderNumber}</h3>

**RESPONSE**
```json
{
    "id": "672662025524c80e99fe6911",
    "orderNumber": "000001",
    "orderDateTime": "2024-10-31T04:52:17.367Z",
    "cpf": "12345",
    "name": "Jose Moreira",
    "itemEntities": [
        {
            "name": "Hot Dog de Salsicha",
            "quantity": 1,
            "price": 20,
            "cookingTime": 10,
            "customization": "Molho extra"
        }
    ],
    "statusOrder": "NOVO",
    "paymentMethod": null,
    "totalPrice": 20,
    "remainingTime": 0,
    "waitingTime": 10
}
```
<h3 id="get-ordersrefactored-sorted">GET /api/ordersrefactored/sorted</h3>

**RESPONSE**  
```json
[
    {
        "id": "672662025524c80e99fe6911",
        "orderNumber": "000001",
        "orderDateTime": "2024-10-31T04:52:17.367Z",
        "cpf": "00000000001",
        "name": "Jose Moreira",
        "itemEntities": [
            {
                "name": "Hot Dog de Salsicha",
                "quantity": 1,
                "price": 20,
                "cookingTime": 10,
            }
        ],
        "statusOrder": "PRONTO",
        "paymentMethod": "Mercado Pago",
        "totalPrice": 20,
        "remainingTime": 0,
        "waitingTime": 10
    },
    {
        "id": "672662025524c80e99fe6912",
        "orderNumber": "000002",
        "orderDateTime": "2024-10-31T05:52:17.367Z",
        "cpf": "00000000002",
        "name": "Maria Silva",
        "itemEntities": [
            {
                "name": "Hamburger",
                "quantity": 2,
                "price": 30,
                "cookingTime": 15,
            }
        ],
        "statusOrder": "PREPARACAO",
        "paymentMethod": "Mercado Pago",
        "totalPrice": 30,
        "remainingTime": 0,
        "waitingTime": 11
    },
    {
        "id": "6768165862f19842658e9432",
        "orderNumber": "000003",
        "orderDateTime": "2024-12-22T13:38:32.008Z",
        "cpf": "04953129326",
        "name": "fulano da silva",
        "itemEntities": [
            {
                "name": "Coca-Cola 350ml",
                "quantity": 2,
                "price": 8,
                "cookingTime": 1,
                "customization": "1 das cocas sem gelo"
            }
        ],
        "statusOrder": "RECEBIDO",
        "paymentMethod": "Mercado Pago",
        "totalPrice": 16,
        "remainingTime": 0,
        "waitingTime": 2
    }
]
<h3 id="post-ordersrefactored">POST /api/ordersrefactored</h3>

**REQUEST**  
```json
{
    "cpf": "01234567894", // CPF must be previously enrolled (see POST /api/user/auth/signup) 
    "items": [ //Product names must follow existing itemEntities in Products collection (see GET /api/productsv2)
        {
            "name": "Pizza de Calabresa",
            "quantity": 1,
            "customization": "queijo extra" // Optional field
        },
        {
            "name": "Coca-Cola 350ml",
            "quantity": 2
        },
        {
            "name": "Sundae de Chocolate",
            "quantity": 3
        }
    ]
}
```
**RESPONSE**
```json
{
    "id": "67266dffb6941f69258b3919",
    "orderNumber": "000002",
    "orderDateTime": "2024-11-02T18:22:55.239740534Z",
    "cpf": "01234567894",
    "name": "Fulano de Souza",
    "itemEntities": [
        {
            "name": "Pizza de Calabresa",
            "quantity": 1,
            "price": 25,
            "cookingTime": 12,
            "customization": "queijo extra"
        },
        {
            "name": "Coca-Cola 350ml",
            "quantity": 2,
            "price": 8,
            "cookingTime": 1,
            "customization": ""
        },
        {
            "name": "Sundae de Chocolate",
            "quantity": 3,
            "price": 10,
            "cookingTime": 5,
            "customization": ""
        }
    ],
    "statusOrder": "NOVO",
    "paymentMethod": null,
    "totalPrice": 71,
    "remainingTime": 0,
    "waitingTime": 29
}
```

<h3 id="put-ordersrefactored">PUT /api/ordersrefactored/{id}</h3>

**REQUEST**  
```json
{
  "id": "672662a6b6941f69258b3918",
  "cpf": "01234567890",
  "items": [
    {
      "name": "Coca-Cola 350ml",
      "quantity": 2,
      "customization": "1 das cocas sem gelo"
    }
  ]
}
```

**RESPONSE**  
```json
{
    "id": "672662a6b6941f69258b3918",
    "orderNumber": "000002",
    "orderDateTime": "2024-11-02T17:34:30.979Z",
    "cpf": "01234567890",
    "name": "Fulano de Souza",
    "itemEntities": [
        {
            "name": "Coca-Cola 350ml",
            "quantity": 2,
            "price": 8,
            "cookingTime": 1,
            "customization": "1 das cocas sem gelo"
        }
    ],
    "statusOrder": "NOVO",
    "paymentMethod": null,
    "totalPrice": 16,
    "remainingTime": 0,
    "waitingTime": 2
}
```

<h3 id="put-ordersrefactored-status">PUT /api/ordersrefactored/status</h3>

**REQUEST**  
```json
{
    "orderId": "6790ec5c5732bc6b3c263bf9",
    "status": "PAGO"
}
```

**RESPONSE**  
```json
{
    "id": "6790ec5c5732bc6b3c263bf9",
    "orderNumber": "000004",
    "orderDateTime": "2025-01-22T13:02:20.599Z",
    "cpf": "01234567894",
    "name": "fulano da silva",
    "items": [
        {
            "name": "Ovos mexidos",
            "quantity": 3,
            "price": 5.00,
            "cookingTime": 5,
            "customization": ""
        }
    ],
    "statusOrder": "PAGO",
    "paymentMethod": null,
    "totalPrice": 15.00,
    "remainingTime": 0,
    "waitingTime": 15
}
```
