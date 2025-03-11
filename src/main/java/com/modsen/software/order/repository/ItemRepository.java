package com.modsen.software.order.repository;

import com.modsen.software.order.model.Item;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
public interface ItemRepository extends ReactiveCrudRepository<Item, Long> {
    Flux<Item> findByOrderId(Long orderId);

    @Query(value = "DELETE FROM items WHERE id = :id RETURNING order_id")
    @Modifying
    Mono<Long> deleteItemByIdReturningId(Long id);

    @Query(value = "SELECT SUM( price * quantity ) FROM items WHERE order_id = :orderId")
    Mono<BigDecimal> findSumOfAmountByOrderId(Long orderId);

    @Query(value = "DELETE FROM items WHERE order_id = :orderId")
    @Modifying
    Mono<Void> deleteItemsByOrderId(Long orderId);
}
