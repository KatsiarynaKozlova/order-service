package com.modsen.software.order.service;

import com.modsen.software.order.exception.ItemNotFoundException;
import com.modsen.software.order.model.Item;
import com.modsen.software.order.repository.ItemRepository;
import com.modsen.software.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static com.modsen.software.order.util.ExceptionMessages.ITEM_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public Flux<Item> getItemsByOrderId(Long orderId) {
        return itemRepository.findByOrderId(orderId);
    }

    public Mono<Item> getItemById(Long id) {
        return getItemByIdOrElseThrown(id);
    }

    public Mono<Item> createItem(Item item) {
        return itemRepository.save(item);
    }

    public Flux<Item> addItemsToDelivery(List<Item> itemList, Long orderId) {
        return Flux.fromIterable(itemList)
                .flatMap(item -> {
                    item.setOrderId(orderId);
                    return itemRepository.save(item);
                });
    }

    @Transactional
    public Mono<Void> deleteItemFromOrder(Long itemId) {
        return itemRepository.deleteItemByIdReturningId(itemId)
                .switchIfEmpty(Mono.error(
                        new ItemNotFoundException(String.format(ITEM_NOT_FOUND_EXCEPTION, itemId))
                ))
                .flatMap(orderId ->
                        itemRepository.findSumOfAmountByOrderId(orderId)
                                .defaultIfEmpty(BigDecimal.ZERO)
                                .flatMap(amount -> {
                                    if (amount.equals(BigDecimal.ZERO)) return orderRepository.deleteById(orderId);
                                    else return orderRepository.updateTotalPriceById(amount, orderId).then();
                                })).then();
    }

    public Mono<Void> deleteAllItemsFromOrder(Long orderId) {
        return itemRepository.deleteItemsByOrderId(orderId);
    }

    private Mono<Item> getItemByIdOrElseThrown(Long id) {
        return itemRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ItemNotFoundException(String.format(ITEM_NOT_FOUND_EXCEPTION, id))
                ));
    }
}
