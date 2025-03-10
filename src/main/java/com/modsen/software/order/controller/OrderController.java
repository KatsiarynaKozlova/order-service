package com.modsen.software.order.controller;

import com.modsen.software.order.dto.request.DeliveryRequest;
import com.modsen.software.order.dto.request.OrderStatusRequest;
import com.modsen.software.order.dto.response.ItemListResponse;
import com.modsen.software.order.dto.response.ItemResponse;
import com.modsen.software.order.dto.response.OrderResponse;
import com.modsen.software.order.kafka.producer.OrderProducer;
import com.modsen.software.order.mapper.ItemMapper;
import com.modsen.software.order.mapper.OrderMapper;
import com.modsen.software.order.model.Item;
import com.modsen.software.order.model.Order;
import com.modsen.software.order.service.ItemService;
import com.modsen.software.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ItemService itemService;
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;
    private final OrderProducer orderProducer;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<OrderResponse>> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(orderMapper.toOrderResponse(order)));
    }

    @GetMapping("/{id}/details")
    public Mono<ResponseEntity<ItemListResponse>> getOrderDetailsById(@PathVariable Long id) {
        return itemService.getItemsByOrderId(id)
                .collectList()
                .map(items -> {
                    List<ItemResponse> itemResponseList = itemMapper.toListOfItemResponse(items);
                    return ResponseEntity.ok(new ItemListResponse(itemResponseList));
                });
    }

    @PostMapping
    @Transactional
    public Mono<ResponseEntity<OrderResponse>> createOrder(@RequestBody DeliveryRequest deliveryRequest) {
        List<Item> itemList = itemMapper.toListOfItems(deliveryRequest.itemListRequest().itemRequestList());
        Order newOrder = orderMapper.toOrderModel(deliveryRequest);
        return orderService.createOrder(itemList, newOrder).map(order -> {
            itemService.addItemsToDelivery(itemList, order.getId());
            return ResponseEntity.ok(orderMapper.toOrderResponse(order));
        });
    }

    @PutMapping("{id}/status")
    public Mono<ResponseEntity<OrderResponse>> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusRequest statusRequest) {
        return orderService.updateOrderStatus(id, statusRequest.status())
                .map(order -> {
                    orderProducer.sendOrderStatusNotification(orderMapper.toStatusDetails(order));
                    return ResponseEntity.ok(orderMapper.toOrderResponse(order));
                });
    }
}
