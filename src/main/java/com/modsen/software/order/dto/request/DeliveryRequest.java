package com.modsen.software.order.dto.request;

public record DeliveryRequest(ItemListRequest itemListRequest, String pickupLocation, String deliveryLocation) {
}
