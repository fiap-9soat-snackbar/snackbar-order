package com.snackbar.order.infrastructure.persistence;

import com.snackbar.order.domain.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRefactoryRepository extends MongoRepository<OrderEntity, String> {
    Optional<Order> findTopByOrderByOrderNumberDesc();
    List<OrderEntity> findAllByOrderByOrderNumberAsc();
    Optional<OrderEntity> findByOrderNumber(String orderNumber);
}
