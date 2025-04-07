package com.snackbar.orderRefactory.infrastructure.controllers;

public record UpdateOrderStatusRequest(
    String orderId,
    String status
) {}