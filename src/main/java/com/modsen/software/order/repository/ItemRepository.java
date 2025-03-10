package com.modsen.software.order.repository;

import com.modsen.software.order.model.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ItemRepository extends ReactiveCrudRepository<Item, Long> {
    Flux<Item> findByOrderId(Long orderId);
}
