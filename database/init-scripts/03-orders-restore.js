// Conectar ao MongoDB
db = db.getSiblingDB('snackbar');

// Inserir registros na collection 'orders'
db.orders.insertMany([
  {
    "_id": ObjectId("678d6a63ec453308f7087d0c"),
    "orderNumber": "000002",
    "orderDateTime": ISODate("2025-01-20T22:10:20.679Z"),
    "cpf": "00000000001",
    "name": "Jose Moreira",
    "items": [
      {
        "name": "Hambúrguer",
        "quantity": 3,
        "price": "22",
        "cookingTime": 10,
        "customization": ""
      }
    ],
    "statusOrder": "PAGO",
    "paymentMethod": "mercado_pago",
    "totalPrice": "66",
    "remainingTime": NumberLong(0),
    "_class": "com.snackbar.orderRefactory.infrastructure.persistence.OrderEntity"
  },
  {
    "_id": ObjectId("678edc6c4a8c471ec21e665f"),
    "orderNumber": "000001",
    "orderDateTime": ISODate("2025-01-20T23:29:48.679Z"),
    "cpf": "00000000002",
    "name": "Rosangela Santos",
    "items": [
      {
        "name": "Hambúrguer",
        "quantity": 1,
        "price": "22",
        "cookingTime": 10,
        "customization": ""
      }
    ],
    "statusOrder": "PAGO",
    "paymentMethod": "mercado_pago",
    "totalPrice": "22",
    "remainingTime": NumberLong(0),
    "_class": "com.snackbar.orderRefactory.infrastructure.persistence.OrderEntity"
  }
]);

print("Collection 'orders' restored.");
