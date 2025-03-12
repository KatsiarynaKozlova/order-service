package com.modsen.software.order.dto.request;

import com.modsen.software.order.model.OrderStatus;

public record OrderStatusRequest(OrderStatus status) {
}
