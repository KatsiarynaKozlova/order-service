package com.modsen.software.order.service;

import com.modsen.software.order.exception.OrderNotFoundException;
import com.modsen.software.order.model.Item;
import com.modsen.software.order.model.Order;
import com.modsen.software.order.model.OrderStatus;
import com.modsen.software.order.repository.OrderRepository;
import com.modsen.software.order.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Mono<Order> getOrderById(Long id) {
        return getOrderByIdOrELseThrown(id);
    }

    public Mono<Order> createOrder(List<Item> itemList, Order newOrder) {
        BigDecimal amount = getAmount(itemList);
        newOrder.setTotalPrice(amount);
        return orderRepository.save(newOrder);
    }

    public Mono<Order> updateOrderStatus(Long id, OrderStatus status) {
        return getByIdLockedOrElseThrow(id).flatMap(order -> {
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            return orderRepository.save(order);
        });
    }

    private Mono<Order> getOrderByIdOrELseThrown(Long id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new OrderNotFoundException(String.format(ExceptionMessages.ORDER_NOT_FOUND_EXCEPTION, id))
                ));
    }

    private Mono<Order> getByIdLockedOrElseThrow(Long id) {
        return orderRepository.findByIdLocked(id)
                .switchIfEmpty(Mono.error(
                        new OrderNotFoundException(String.format(ExceptionMessages.ORDER_NOT_FOUND_EXCEPTION, id))
                ));
    }

    private BigDecimal getAmount(List<Item> itemList) {
        return itemList.stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
