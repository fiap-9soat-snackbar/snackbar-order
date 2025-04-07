package com.snackbar.orderRefactory.config;

import com.snackbar.iam.application.UserService;
import com.snackbar.orderRefactory.infrastructure.gateways.ProductDTOToOrderItemMapper;
import com.snackbar.orderRefactory.infrastructure.gateways.ProductServiceGateway;
import com.snackbar.product.usecase.impl.GetProductByNameUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.snackbar.orderRefactory.application.usecases.OrderUseCase;
import com.snackbar.orderRefactory.infrastructure.controllers.OrderDTOMapper;
import com.snackbar.orderRefactory.infrastructure.gateways.OrderEntityMapper;
import com.snackbar.orderRefactory.infrastructure.gateways.OrderRepositoryGateway;
import com.snackbar.orderRefactory.infrastructure.persistence.OrderRefactoryRepository;

@Configuration
public class OrderConfig {
    @Bean
    OrderUseCase orderUseCase(
            OrderRepositoryGateway orderRepositoryGateway,
            ProductServiceGateway productServiceGateway,
            UserService userService
    ) {
        return new OrderUseCase(orderRepositoryGateway, productServiceGateway, userService);
    }

    @Bean
    OrderRepositoryGateway orderRepositoryGateway(OrderRefactoryRepository orderRefactoryRepository, OrderEntityMapper orderEntityMapper) {
        return new OrderRepositoryGateway(orderRefactoryRepository, orderEntityMapper);
    }

    @Bean
    ProductDTOToOrderItemMapper productDTOToOrderItemMapper() {
        return new ProductDTOToOrderItemMapper();
    }

    @Bean
    ProductServiceGateway productServiceGateway(GetProductByNameUseCaseImpl productUseCase, ProductDTOToOrderItemMapper productDTOToOrderItemMapper) {
        return new ProductServiceGateway(productUseCase, productDTOToOrderItemMapper);
    }

    @Bean
    OrderEntityMapper orderEntityMapper() {
        return new OrderEntityMapper();
    }

    @Bean
    OrderDTOMapper orderDTOMapper() {
        return new OrderDTOMapper();
    }
}
