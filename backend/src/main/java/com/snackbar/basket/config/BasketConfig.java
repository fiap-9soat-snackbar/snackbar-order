package com.snackbar.basket.config;


import com.snackbar.basket.application.usecases.BasketUseCase;
import com.snackbar.basket.infrastructure.controllers.BasketDTOMapper;
import com.snackbar.basket.infrastructure.gateways.BasketEntityMapper;
import com.snackbar.basket.infrastructure.gateways.BasketRepositoryGateway;
import com.snackbar.basket.infrastructure.gateways.ItemEntityMapper;
import com.snackbar.basket.infrastructure.persistence.BasketRepository;
import com.snackbar.productintegration.usecase.GetProductByNameUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasketConfig {

    @Bean
    public ItemEntityMapper itemEntityMapper() {
        return new ItemEntityMapper();
    }

    @Bean
    public BasketEntityMapper basketEntityMapper() {
        return new BasketEntityMapper();
    }

    @Bean
    public BasketRepositoryGateway basketRepositoryGateway(BasketRepository basketRepository, BasketEntityMapper basketEntityMapper, ItemEntityMapper itemEntityMapper) {
        return new BasketRepositoryGateway(basketRepository, basketEntityMapper, itemEntityMapper);
    }

    @Bean
    public BasketUseCase basketUseCase(
            BasketRepositoryGateway basketRepositoryGateway,
            GetProductByNameUseCase getProductByNameUseCase

    ) {
        return new BasketUseCase(basketRepositoryGateway, getProductByNameUseCase);
    }

    @Bean
    public BasketDTOMapper basketDTOMapper() {
        return new BasketDTOMapper();
    }
}
