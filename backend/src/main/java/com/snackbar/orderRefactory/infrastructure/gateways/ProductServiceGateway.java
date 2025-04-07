package com.snackbar.orderRefactory.infrastructure.gateways;

import com.snackbar.orderRefactory.application.gateways.ProductGateway;
import com.snackbar.orderRefactory.domain.entity.OrderItem;
import com.snackbar.product.usecase.impl.GetProductByNameUseCaseImpl;

public class ProductServiceGateway implements ProductGateway {

    private final GetProductByNameUseCaseImpl productUseCase;
    private final ProductDTOToOrderItemMapper productDTOToOrderItemMapper;

    public ProductServiceGateway(
            GetProductByNameUseCaseImpl productUseCase,
            ProductDTOToOrderItemMapper productDTOToOrderItemMapper
    ) {
        this.productUseCase = productUseCase;
        this.productDTOToOrderItemMapper = productDTOToOrderItemMapper;
    }

    @Override
    public OrderItem getProductByName(String name) {
        var productEntity = productUseCase.getProductByName(name);
        return this.productDTOToOrderItemMapper.toOrderItem(productEntity);
    }
}
