 
## 📍Basket Endpoints

| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /api/basket</kbd>     | See [request details](#get-basket)
| <kbd>GET /api/basket/{id}</kbd>     |  See [request details](#get-basket-id)
| <kbd>POST /api/basket</kbd>     | See [request details](#post-basket)
| <kbd>POST /api/basket/{basketId}/items</kbd>     | See [request details](#post-basket-items)
| <kbd>DELETE /api/basket/{basketId}/items/{name}</kbd>     | See [request details](#delete-basket-items) 


<h3 id="get-basket">GET /api/basket</h3>

**RESPONSE**  
```json
[
    {
        "id": "6790e13af168e54e6f5e9d7d",
        "cpf": "01234567890",
        "items": [
            {
                "name": "Ovos mexidos",
                "quantity": 2,
                "price": 5.00,
                "cookingTime": 5
            }
        ],
        "totalPrice": 10.00
    },
    /* All other baskets */
    
]

```

<h3 id="get-basket-id">GET /api/basket/{id}</h3>

**RESPONSE**
```json
{
    "id": "6790e187f168e54e6f5e9d7e",
    "cpf": "01234567890",
    "items": [
        {
            "name": "Hambúrguer",
            "quantity": 3,
            "price": 22,
            "cookingTime": 10
        }
    ],
    "totalPrice": 66
}

```

<h3 id="post-basket">POST /api/basket</h3>

**REQUEST**  
```json
{
    "cpf":"01234567890",
    "items": [
        {
            "name": "Ovos mexidos",
            "quantity": 2
        }
    ]
}
```
**RESPONSE**
```json
{
    "id": "6790e13af168e54e6f5e9d7d",
    "cpf": "01234567890",
    "items": [
        {
            "name": "Ovos mexidos",
            "quantity": 2,
            "price": 5.00,
            "cookingTime": 5
        }
    ],
    "totalPrice": 10.00
}

```

<h3 id="post-basket-items">POST /api/basket/{basketId}/items</h3>

**REQUEST**  
```json
{
    "cpf":"01234567890",
    "items": [
        {
            "name": "Hambúrguer",
            "quantity": 1
        }
    ]
}
```

**RESPONSE**  
```json
{
    "id": "6790e13af168e54e6f5e9d7d",
    "cpf": "01234567890",
    "items": [
        {
            "name": "Ovos mexidos",
            "quantity": 2,
            "price": 5.00,
            "cookingTime": 5
        },
        {
            "name": "Hambúrguer",
            "quantity": 1,
            "price": 22,
            "cookingTime": 10
        }
    ],
    "totalPrice": 32.00
}

```
<h3 id="delete-basket-items">DELETE /api/basket/{basketId}/items/{name}</h3>

**RESPONSE**
HTTP 200 Only