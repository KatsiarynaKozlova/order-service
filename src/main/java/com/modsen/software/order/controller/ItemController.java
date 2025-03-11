package com.modsen.software.order.controller;

import com.modsen.software.order.dto.request.ItemRequest;
import com.modsen.software.order.dto.response.ItemResponse;
import com.modsen.software.order.mapper.ItemMapper;
import com.modsen.software.order.model.Item;
import com.modsen.software.order.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ItemResponse>> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(item -> ResponseEntity.ok(itemMapper.toItemResponse(item)));
    }

    @PostMapping
    public Mono<ResponseEntity<ItemResponse>> addItem(@RequestBody ItemRequest itemRequest) {
        Item newItem = itemMapper.toItemModel(itemRequest);
        return itemService.createItem(newItem)
                .map(item -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(itemMapper.toItemResponse(item)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteItemFromDelivery(@PathVariable Long id) {
        return itemService.deleteItemFromOrder(id);
    }
}
