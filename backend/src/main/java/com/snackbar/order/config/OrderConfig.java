package com.snackbar.order.config;

import com.snackbar.iamIntegration.application.UserService;
import com.snackbar.order.application.usecases.OrderUseCase;
import com.snackbar.order.infrastructure.controllers.OrderDTOMapper;
import com.snackbar.order.infrastructure.gateways.OrderEntityMapper;
import com.snackbar.order.infrastructure.gateways.OrderRepositoryGateway;
import com.snackbar.order.infrastructure.gateways.ProductDTOToOrderItemMapper;
import com.snackbar.order.infrastructure.gateways.ProductServiceGateway;
import com.snackbar.order.infrastructure.persistence.OrderRefactoryRepository;
import com.snackbar.productintegration.usecase.impl.GetProductByNameUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
