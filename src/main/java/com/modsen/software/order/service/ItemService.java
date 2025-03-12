package com.modsen.software.order.service;

import com.modsen.software.order.exception.ItemNotFoundException;
import com.modsen.software.order.model.Item;
import com.modsen.software.order.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.modsen.software.order.util.ExceptionMessages.ITEM_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

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

    private Mono<Item> getItemByIdOrElseThrown(Long id) {
        return itemRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ItemNotFoundException(String.format(ITEM_NOT_FOUND_EXCEPTION, id))
                ));
    }
}
