package com.snackbar.checkout.gateway;

import com.snackbar.checkout.entity.Checkout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckoutRepository extends MongoRepository<Checkout, String> {
    Optional<Checkout> findByOrderId(String orderId);
}
