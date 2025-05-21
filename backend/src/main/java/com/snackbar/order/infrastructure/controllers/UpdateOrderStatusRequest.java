package com.snackbar.order.infrastructure.controllers;

public record UpdateOrderStatusRequest(
    String orderId,
    String status
) {}