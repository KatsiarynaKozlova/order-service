package com.modsen.software.order.dto.response;

import java.util.List;

public record DeliveryResponse(OrderResponse orderResponse, List<ItemResponse> items) {
}
