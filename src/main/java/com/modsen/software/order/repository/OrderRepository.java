package com.modsen.software.order.repository;

import com.modsen.software.order.model.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
    @Query("SELECT * FROM orders WHERE id = :id FOR UPDATE")
    Mono<Order> findByIdLocked(Long id);
}
